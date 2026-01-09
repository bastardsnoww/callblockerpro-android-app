package com.callblockerpro.app.domain.logic

import com.callblockerpro.app.domain.repository.RemoteConfigRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class EmergencyNumberValidatorTest {

    private lateinit var remoteConfigRepository: RemoteConfigRepository
    private lateinit var validator: EmergencyNumberValidator

    private val emergencyNumbersFlow = MutableStateFlow<Set<String>>(emptySet())

    @Before
    fun setup() {
        remoteConfigRepository = mockk(relaxed = true)
        every { remoteConfigRepository.emergencyNumbers } returns emergencyNumbersFlow
        
        validator = EmergencyNumberValidator(remoteConfigRepository)
    }

    @Test
    fun `isEmergencyNumber returns true for hardcoded fallback numbers`() {
        assertTrue(validator.isEmergencyNumber("911"))
        assertTrue(validator.isEmergencyNumber("112"))
        assertTrue(validator.isEmergencyNumber("999"))
        assertTrue(validator.isEmergencyNumber("000"))
    }

    @Test
    fun `isEmergencyNumber returns true for remote config numbers`() {
        // Given
        emergencyNumbersFlow.value = setOf("12345")

        // Then
        assertTrue(validator.isEmergencyNumber("12345"))
        assertTrue(validator.isEmergencyNumber("+1-12345")) // endsWith check
    }

    @Test
    fun `isEmergencyNumber returns false for normal numbers`() {
        assertFalse(validator.isEmergencyNumber("1234567890"))
        assertFalse(validator.isEmergencyNumber("555-5555"))
    }

    @Test
    fun `isEmergencyNumber handles country codes logic for fallback`() {
        // Fallback "911"
        assertTrue(validator.isEmergencyNumber("+1911"))
        assertTrue(validator.isEmergencyNumber("00911"))
    }
}
