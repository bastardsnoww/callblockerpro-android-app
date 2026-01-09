package com.callblockerpro.app.domain.logic

import com.callblockerpro.app.domain.model.AppMode
import com.callblockerpro.app.domain.model.CallResult
import com.callblockerpro.app.domain.util.PhoneNumberUtils

/**
 * Represents the result of a call screening decision.
 *
 * @property result The action to take on the call ([CallResult.ALLOWED] or [CallResult.BLOCKED])
 * @property reason The rationale for the decision
 */
data class CallDecision(
    val result: CallResult,
    val reason: DecisionReason
)

/**
 * Enumeration of possible reasons for a call decision.
 *
 * Used for logging, analytics, and debugging call screening behavior.
 */
enum class DecisionReason {
    /** Call allowed because app is in neutral mode and number is not blocked */
    NEUTRAL_MODE,
    
    /** Call allowed because number is in the whitelist */
    WHITELIST_MATCH,
    
    /** Call blocked because number is not in the whitelist (whitelist mode) */
    NOT_IN_WHITELIST,
    
    /** Call blocked because number is in the blocklist */
    BLOCKLIST_MATCH,
    
    /** Call allowed because number is not in the blocklist */
    NOT_IN_BLOCKLIST
}

/**
 * Core business logic engine for determining call blocking decisions.
 *
 * Evaluates incoming calls against the current app mode and user-defined
 * lists (whitelist/blocklist) to determine whether a call should be allowed or blocked.
 *
 * ## Decision Matrix
 *
 * | Mode | Whitelisted | Blocklisted | Decision |
 * |:---|:---:|:---:|:---|
 * | **NEUTRAL** | - | Yes | BLOCKED |
 * | **NEUTRAL** | - | No | ALLOWED |
 * | **WHITELIST** | Yes | - | ALLOWED |
 * | **WHITELIST** | No | - | BLOCKED |
 * | **BLOCKLIST** | - | Yes | BLOCKED |
 * | **BLOCKLIST** | - | No | ALLOWED |
 *
 * ## Usage Example
 *
 * ```kotlin
 * val engine = CallDecisionEngine()
 * val decision = engine.determineAction(
 *     mode = AppMode.WHITELIST,
 *     incomingNumber = "+1234567890",
 *     isWhitelisted = true,
 *     isBlocklisted = false
 * )
 * // decision.result == CallResult.ALLOWED
 * // decision.reason == DecisionReason.WHITELIST_MATCH
 * ```
 *
 * @see AppMode for available blocking modes
 * @see CallDecision for decision output structure
 */
class CallDecisionEngine {

    /**
     * Determines the appropriate action for an incoming call.
     *
     * This is a pure function with no side effects, making it easily testable
     * and safe to call from any thread.
     *
     * @param mode Current app blocking mode (NEUTRAL, WHITELIST, or BLOCKLIST)
     * @param incomingNumber The phone number of the incoming call (normalized)
     * @param isWhitelisted True if the number exists in the user's allowlist
     * @param isBlocklisted True if the number exists in the user's blocklist
     * @return [CallDecision] containing the result and reasoning
     *
     * @see AppMode
     * @see CallDecision
     * @see DecisionReason
     */
    fun determineAction(
        mode: AppMode,
        incomingNumber: String,
        isWhitelisted: Boolean,
        isBlocklisted: Boolean
    ): CallDecision {
        return when (mode) {
            AppMode.NEUTRAL -> {
                // Neutral Mode: Monitor only. Logs all calls but blocks none.
                CallDecision(CallResult.ALLOWED, DecisionReason.NEUTRAL_MODE)
            }
            AppMode.WHITELIST -> {
                if (isWhitelisted) {
                    CallDecision(CallResult.ALLOWED, DecisionReason.WHITELIST_MATCH)
                } else {
                    CallDecision(CallResult.BLOCKED, DecisionReason.NOT_IN_WHITELIST)
                }
            }
            AppMode.BLOCKLIST -> {
                if (isBlocklisted) {
                    CallDecision(CallResult.BLOCKED, DecisionReason.BLOCKLIST_MATCH)
                } else {
                    CallDecision(CallResult.ALLOWED, DecisionReason.NOT_IN_BLOCKLIST)
                }
            }
        }
    }
}
