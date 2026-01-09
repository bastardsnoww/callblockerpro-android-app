package com.callblockerpro.app.domain.logic

import com.callblockerpro.app.domain.model.AppMode
import com.callblockerpro.app.domain.model.CallResult
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for [CallDecisionEngine].
 *
 * Tests all decision paths for the three app modes: NEUTRAL, WHITELIST, BLOCKLIST.
 */
class CallDecisionEngineTest {

    private lateinit var engine: CallDecisionEngine

    @Before
    fun setUp() {
        engine = CallDecisionEngine()
    }

    // ========== NEUTRAL MODE TESTS ==========

    @Test
    fun `neutral mode - blocked number returns BLOCKED`() {
        // Given
        val mode = AppMode.NEUTRAL
        val number = "+1234567890"
        val isWhitelisted = false
        val isBlocklisted = true

        // When
        val decision = engine.determineAction(mode, number, isWhitelisted, isBlocklisted)

        // Then
        assertThat(decision.result).isEqualTo(CallResult.BLOCKED)
        assertThat(decision.reason).isEqualTo(DecisionReason.BLOCKLIST_MATCH)
    }

    @Test
    fun `neutral mode - non-blocked number returns ALLOWED`( ) {
        // Given
        val mode = AppMode.NEUTRAL
        val number = "+1234567890"
        val isWhitelisted = false
        val isBlocklisted = false

        // When
        val decision = engine.determineAction(mode, number, isWhitelisted, isBlocklisted)

        // Then
        assertThat(decision.result).isEqualTo(CallResult.ALLOWED)
        assertThat(decision.reason).isEqualTo(DecisionReason.NEUTRAL_MODE)
    }

    @Test
    fun `neutral mode - whitelisted but also blocked returns BLOCKED blocklist takes precedence`() {
        // Given
        val mode = AppMode.NEUTRAL
        val number = "+1234567890"
        val isWhitelisted = true
        val isBlocklisted = true

        // When
        val decision = engine.determineAction(mode, number, isWhitelisted, isBlocklisted)

        // Then
        assertThat(decision.result).isEqualTo(CallResult.BLOCKED)
        assertThat(decision.reason).isEqualTo(DecisionReason.BLOCKLIST_MATCH)
    }

    // ========== WHITELIST MODE TESTS ==========

    @Test
    fun `whitelist mode - whitelisted number returns ALLOWED`() {
        // Given
        val mode = AppMode.WHITELIST
        val number = "+1234567890"
        val isWhitelisted = true
        val isBlocklisted = false

        // When
        val decision = engine.determineAction(mode, number, isWhitelisted, isBlocklisted)

        // Then
        assertThat(decision.result).isEqualTo(CallResult.ALLOWED)
        assertThat(decision.reason).isEqualTo(DecisionReason.WHITELIST_MATCH)
    }

    @Test
    fun `whitelist mode - non-whitelisted number returns BLOCKED`() {
        // Given
        val mode = AppMode.WHITELIST
        val number = "+1234567890"
        val isWhitelisted = false
        val isBlocklisted = false

        // When
        val decision = engine.determineAction(mode, number, isWhitelisted, isBlocklisted)

        // Then
        assertThat(decision.result).isEqualTo(CallResult.BLOCKED)
        assertThat(decision.reason).isEqualTo(DecisionReason.NOT_IN_WHITELIST)
    }

    @Test
    fun `whitelist mode - whitelisted overrides blocklist`() {
        // Given
        val mode = AppMode.WHITELIST
        val number = "+1234567890"
        val isWhitelisted = true
        val isBlocklisted = true

        // When
        val decision = engine.determineAction(mode, number, isWhitelisted, isBlocklisted)

        // Then
        assertThat(decision.result).isEqualTo(CallResult.ALLOWED)
        assertThat(decision.reason).isEqualTo(DecisionReason.WHITELIST_MATCH)
    }

    // ========== BLOCKLIST MODE TESTS ==========

    @Test
    fun `blocklist mode - blocked number returns BLOCKED`() {
        // Given
        val mode = AppMode.BLOCKLIST
        val number = "+1234567890"
        val isWhitelisted = false
        val isBlocklisted = true

        // When
        val decision = engine.determineAction(mode, number, isWhitelisted, isBlocklisted)

        // Then
        assertThat(decision.result).isEqualTo(CallResult.BLOCKED)
        assertThat(decision.reason).isEqualTo(DecisionReason.BLOCKLIST_MATCH)
    }

    @Test
    fun `blocklist mode - non-blocked number returns ALLOWED`() {
        // Given
        val mode = AppMode.BLOCKLIST
        val number = "+1234567890"
        val isWhitelisted = false
        val isBlocklisted = false

        // When
        val decision = engine.determineAction(mode, number, isWhitelisted, isBlocklisted)

        // Then
        assertThat(decision.result).isEqualTo(CallResult.ALLOWED)
        assertThat(decision.reason).isEqualTo(DecisionReason.NOT_IN_BLOCKLIST)
    }

    @Test
    fun `blocklist mode - non-blocked but whitelisted still returns ALLOWED`() {
        // Given
        val mode = AppMode.BLOCKLIST
        val number = "+1234567890"
        val isWhitelisted = true
        val isBlocklisted = false

        // When
        val decision = engine.determineAction(mode, number, isWhitelisted, isBlocklisted)

        // Then
        assertThat(decision.result).isEqualTo(CallResult.ALLOWED)
        assertThat(decision.reason).isEqualTo(DecisionReason.NOT_IN_BLOCKLIST)
    }

    // ========== EDGE CASES ==========

    @Test
    fun `all modes - empty phone number is handled correctly`() {
        // Test that empty numbers do not crash the engine
        val modes = listOf(AppMode.NEUTRAL, AppMode.WHITELIST, AppMode.BLOCKLIST)
        
        modes.forEach { mode ->
            val decision = engine.determineAction(
                mode = mode,
                incomingNumber = "",
                isWhitelisted = false,
                isBlocklisted = false
            )
            
            // Should not throw exception
            assertThat(decision).isNotNull()
        }
    }
}
