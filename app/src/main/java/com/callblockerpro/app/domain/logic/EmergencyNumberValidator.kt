package com.callblockerpro.app.domain.logic

import com.callblockerpro.app.domain.repository.RemoteConfigRepository
import com.google.i18n.phonenumbers.PhoneNumberUtil
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmergencyNumberValidator @Inject constructor(
    private val remoteConfigRepository: RemoteConfigRepository
) {
    companion object {
        // Combined list of hardcoded numbers to be used as fallback
        private val FALLBACK_EMERGENCY_NUMBERS = setOf(
            "112", "911", "999", "000", "110", "119", "118", "113",
            "100", "101", "102", "108", "115", "117"
        )
    }

    /**
     * Determines if a phone number is an emergency number
     * Checks:
     * 1. Remote Config (dynamic update)
     * 2. LibPhoneNumber (standard library)
     * 3. Hardcoded Fallback (safety net)
     */
    fun isEmergencyNumber(phoneNumber: String): Boolean {
        // 1. Check Remote Config
        val remoteNumbers = remoteConfigRepository.emergencyNumbers.value
        // Simple exact match or endsWith? Emergency numbers are usually short.
        val digits = phoneNumber.filter { it.isDigit() }
        
        if (remoteNumbers.isNotEmpty()) {
            if (remoteNumbers.any { checkMatch(digits, it) }) {
                return true
            }
        }

        // 2. Check LibPhoneNumber
        try {
            val util = com.google.i18n.phonenumbers.ShortNumberInfo.getInstance()
            // Use system locale as default region hint
            val region = java.util.Locale.getDefault().country ?: "US"
            if (util.isEmergencyNumber(phoneNumber, region)) {
                return true
            }
        } catch (e: Exception) {
            // Ignore parsing errors
        }

        // 3. Fallback to hardcoded list
        if (digits.isEmpty()) return false
        
        return FALLBACK_EMERGENCY_NUMBERS.any { checkMatch(digits, it) }
    }

    private fun checkMatch(digits: String, emergencyNumber: String): Boolean {
        return digits == emergencyNumber || 
               (digits.endsWith(emergencyNumber) && 
                digits.length <= emergencyNumber.length + 3)
    }
}
