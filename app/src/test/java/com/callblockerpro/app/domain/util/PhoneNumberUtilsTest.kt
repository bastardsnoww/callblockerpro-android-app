package com.callblockerpro.app.domain.util

import org.junit.Assert.*
import org.junit.Test

/**
 * Unit tests for PhoneNumberUtils E.164 normalization and validation.
 * 
 * These tests ensure that the phone number matching mechanism cannot be bypassed
 * by using different formats of the same number.
 */
class PhoneNumberUtilsTest {

    @Test
    fun `normalize should convert US number to E164 format`() {
        val result = PhoneNumberUtils.normalize("+1 (555) 123-4567", "US")
        assertEquals("+15551234567", result)
    }

    @Test
    fun `normalize should handle number without country code using default region`() {
        val result = PhoneNumberUtils.normalize("(555) 123-4567", "US")
        assertEquals("+15551234567", result)
    }

    @Test
    fun `normalize should handle number with dashes`() {
        val result = PhoneNumberUtils.normalize("555-123-4567", "US")
        assertEquals("+15551234567", result)
    }

    @Test
    fun `normalize should handle number with spaces`() {
        val result = PhoneNumberUtils.normalize("555 123 4567", "US")
        assertEquals("+15551234567", result)
    }

    @Test
    fun `normalize should handle number with plus and country code`() {
        val result = PhoneNumberUtils.normalize("+15551234567", "US")
        assertEquals("+15551234567", result)
    }

    @Test
    fun `normalize should return null for invalid number`() {
        val result = PhoneNumberUtils.normalize("invalid", "US")
        assertNull(result)
    }

    @Test
    fun `normalize should return null for empty string`() {
        val result = PhoneNumberUtils.normalize("", "US")
        assertNull(result)
    }

    @Test
    fun `normalize should return null for too short number`() {
        val result = PhoneNumberUtils.normalize("123", "US")
        assertNull(result)
    }

    @Test
    fun `normalize should handle Indian numbers correctly`() {
        val result = PhoneNumberUtils.normalize("+91 98765 43210", "IN")
        assertEquals("+919876543210", result)
    }

    @Test
    fun `normalize should handle UK numbers correctly`() {
        val result = PhoneNumberUtils.normalize("+44 20 7946 0958", "GB")
        assertEquals("+442079460958", result)
    }

    // Test bypass attack scenarios
    @Test
    fun `same number in different formats should normalize to same E164`() {
        val formats = listOf(
            "+1 (555) 123-4567",
            "15551234567",
            "+15551234567",
            "555-123-4567",
            "(555) 123-4567",
            "+1-555-123-4567",
            "1 555 123 4567"
        )

        val normalized = formats.mapNotNull { PhoneNumberUtils.normalize(it, "US") }

        // All should normalize to the same value
        assertEquals(1, normalized.toSet().size)
        assertEquals("+15551234567", normalized.first())
    }

    @Test
    fun `areEqual should return true for same number in different formats`() {
        assertTrue(PhoneNumberUtils.areEqual("+1 (555) 123-4567", "555-123-4567", "US"))
        assertTrue(PhoneNumberUtils.areEqual("+15551234567", "(555) 123-4567", "US"))
        assertTrue(PhoneNumberUtils.areEqual("555.123.4567", "+1-555-123-4567", "US"))
    }

    @Test
    fun `areEqual should return false for different numbers`() {
        assertFalse(PhoneNumberUtils.areEqual("+15551234567", "+15559999999", "US"))
        assertFalse(PhoneNumberUtils.areEqual("555-123-4567", "555-999-9999", "US"))
    }

    @Test
    fun `areEqual should return false if either number is invalid`() {
        assertFalse(PhoneNumberUtils.areEqual("invalid", "+15551234567", "US"))
        assertFalse(PhoneNumberUtils.areEqual("+15551234567", "invalid", "US"))
        assertFalse(PhoneNumberUtils.areEqual("invalid", "invalid", "US"))
    }

    @Test
    fun `isValid should return true for valid numbers`() {
        assertTrue(PhoneNumberUtils.isValid("+15551234567", "US"))
        assertTrue(PhoneNumberUtils.isValid("(555) 123-4567", "US"))
        assertTrue(PhoneNumberUtils.isValid("+919876543210", "IN"))
    }

    @Test
    fun `isValid should return false for invalid numbers`() {
        assertFalse(PhoneNumberUtils.isValid("invalid", "US"))
        assertFalse(PhoneNumberUtils.isValid("123", "US"))
        assertFalse(PhoneNumberUtils.isValid("", "US"))
    }

    @Test
    fun `formatForDisplay should return national format`() {
        val result = PhoneNumberUtils.formatForDisplay("+15551234567", "US")
        assertEquals("(555) 123-4567", result)
    }

    @Test
    fun `formatForDisplay should return original if invalid`() {
        val invalid = "invalid"
        val result = PhoneNumberUtils.formatForDisplay(invalid, "US")
        assertEquals(invalid, result)
    }

    @Test
    fun `formatInternational should return international format`() {
        val result = PhoneNumberUtils.formatInternational("+15551234567", "US")
        assertEquals("+1 555-123-4567", result)
    }

    @Test
    fun `getCountryCode should return correct country code`() {
        assertEquals(1, PhoneNumberUtils.getCountryCode("+15551234567", "US"))
        assertEquals(91, PhoneNumberUtils.getCountryCode("+919876543210", "IN"))
        assertEquals(44, PhoneNumberUtils.getCountryCode("+442079460958", "GB"))
    }

    @Test
    fun `getCountryCode should return null for invalid number`() {
        assertNull(PhoneNumberUtils.getCountryCode("invalid", "US"))
    }

    // Attack vector tests - ensure these are prevented
    @Test
    fun `international prefix bypass should be prevented`() {
        val blocked = PhoneNumberUtils.normalize("+15551234567", "US")
        val attacker = PhoneNumberUtils.normalize("0015551234567", "US") // International prefix
        
        // Both should normalize to the same E.164 format
        assertEquals(blocked, attacker)
    }

    @Test
    fun `format variations should all match`() {
        val reference = "+15551234567"
        
        val variations = listOf(
            "15551234567",
            "(555) 123-4567",
            "555.123.4567",
            "+1-555-123-4567",
            "1 (555) 123-4567",
            "+1(555)123-4567"
        )
        
        variations.forEach { variant ->
            val normalized = PhoneNumberUtils.normalize(variant, "US")
            assertEquals("Failed for variant: $variant", reference, normalized)
        }
    }
}
