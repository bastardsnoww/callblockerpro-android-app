package com.callblockerpro.app.service

import android.telecom.Call
import android.telecom.CallScreeningService
import com.callblockerpro.app.domain.logic.CallDecisionEngine
import com.callblockerpro.app.domain.model.AppMode
import com.callblockerpro.app.domain.model.CallLogEntry
import com.callblockerpro.app.domain.repository.CallLogRepository
import com.callblockerpro.app.domain.repository.ListRepository
import com.callblockerpro.app.domain.repository.ModeRepository
import com.callblockerpro.app.domain.util.PhoneNumberUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CallBlockingService : CallScreeningService() {

    @Inject
    lateinit var decisionEngine: CallDecisionEngine
    @Inject
    lateinit var listRepository: ListRepository
    @Inject
    lateinit var modeRepository: ModeRepository
    @Inject
    lateinit var emergencyNumberValidator: com.callblockerpro.app.domain.logic.EmergencyNumberValidator
    @Inject
    lateinit var callLogRepository: CallLogRepository

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    // ... (scope definition remains)

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "call_screening_channel"
        private const val NOTIFICATION_CHANNEL_NAME = "Call Screening"
    }

    // ... (onCreate/onDestroy/createNotificationChannel remain)

    override fun onScreenCall(callDetails: Call.Details) {
        val rawNumber = callDetails.handle?.schemeSpecificPart ?: return
        
        // ===================================================================
        // CRITICAL SAFETY CHECK: Emergency numbers MUST be allowed FIRST
        // Legal requirement - blocking emergency calls is a liability
        // ===================================================================
        if (emergencyNumberValidator.isEmergencyNumber(rawNumber)) {
            // Allow emergency call immediately
            respondToCall(callDetails, CallResponse.Builder()
                .setRejectCall(false)
                .build())
            
            // Log for audit purposes (but don't block)
            serviceScope.launch {
                val logEntry = CallLogEntry(
                    phoneNumber = rawNumber,
                    contactName = "Emergency Services",
                    result = com.callblockerpro.app.domain.model.CallResult.ALLOWED,
                    triggerMode = AppMode.NEUTRAL,
                    reason = "EMERGENCY_OVERRIDE"
                )
                callLogRepository.addLogEntry(logEntry)
            }
            return
        }
        
        // OPTIMIZATION: Logic is now fully synchronous (O(1) lookups)
        // No runBlocking or coroutines needed for the decision path.
        
        val mode = modeRepository.getCurrentMode()
        
        // Check if number is whitelisted or blocked (O(1) cache lookups, handles opt normalization)
        val isWhitelisted = listRepository.isWhitelisted(rawNumber)
        val isBlocked = listRepository.isBlocked(rawNumber)
        
        // Pass rawNumber here? DecisionEngine expects normalized but we can normalize inside if needed or pass raw.
        // Actually, ListRepository handles normalization internally if needed.
        // Let's pass raw to decision engine and let it normalize if it needs to logging/comparison? 
        // Decision engine logic is: determineAction(mode, incomingNumber, isWhitelisted, isBlocked)
        // The 'incomingNumber' arg is mostly for logging or if logic depended on area code.
        // For consistent logging, we should normalize the number once for the log entry.
        
        val normalizedNumber = com.callblockerpro.app.domain.util.PhoneNumberUtils.normalize(rawNumber) ?: rawNumber

        val decision = decisionEngine.determineAction(
            mode = mode,
            incomingNumber = normalizedNumber,
            isWhitelisted = isWhitelisted,
            isBlocklisted = isBlocked
        )
        
        val response = CallResponse.Builder()
        
        if (decision.result == com.callblockerpro.app.domain.model.CallResult.BLOCKED) {
            response.setDisallowCall(true)
            response.setRejectCall(true)
            response.setSkipCallLog(false) 
            response.setSkipNotification(true)
        }

        // Call respondToCall synchronously (required by Android)
        respondToCall(callDetails, response.build())

        // Optimization: Offload DB logging to IO thread (non-critical)
        serviceScope.launch {
            val logEntry = CallLogEntry(
                phoneNumber = normalizedNumber,
                contactName = null,
                result = decision.result,
                triggerMode = mode,
                reason = decision.reason.name
            )
            callLogRepository.addLogEntry(logEntry)
        }
    }
}
