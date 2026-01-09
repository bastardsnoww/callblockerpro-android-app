package com.callblockerpro.app.domain.util

import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber

/**
 * Utility object for phone number normalization and validation using E.164 standard.
 * This prevents bypass attacks by ensuring all phone numbers are stored in a consistent format.
 */
object PhoneNumberUtils {
    
    private val phoneUtil = PhoneNumberUtil.getInstance()
    
    /**
     * Default region code for parsing phone numbers without country code.
     * Can be overridden based on user's location/settings.
     */
    const val DEFAULT_REGION = "IN"
    
    /**
     * Normalizes a phone number to E.164 format.
     * 
     * E.164 is the international standard for phone numbers:
     * - Always starts with '+'
     * - Followed by country code and number (digits only)
     * - Example: +15551234567
     * 
     * This ensures that all variations of the same number
     * (e.g., "555-123-4567", "(555) 123-4567", "+1 555 123 4567")
     * are normalized to the same format for reliable matching.
     * 
     * @param phoneNumber The raw phone number (can include formatting)
     * @param defaultRegion Default country code (e.g., "US", "IN", "GB")
     * @return Normalized E.164 format, or null if invalid
     * 
     * @example
     * ```
     * normalize("+1 (555) 123-4567") // Returns: "+15551234567"
     * normalize("555-123-4567", "US") // Returns: "+15551234567"
     * normalize("invalid") // Returns: null
     * ```
     */
    fun normalize(phoneNumber: String, defaultRegion: String = DEFAULT_REGION): String? {
        if (phoneNumber.isBlank()) return null
        
        return try {
            // Parse the phone number
            val parsed = phoneUtil.parse(phoneNumber, defaultRegion)
            
            // Validate it's a real number
            if (phoneUtil.isValidNumber(parsed)) {
                // Format to E.164 (e.g., +15551234567)
                phoneUtil.format(parsed, PhoneNumberUtil.PhoneNumberFormat.E164)
            } else {
                null
            }
        } catch (e: NumberParseException) {
            // Invalid format - return null
            null
        }
    }
    
    /**
     * Checks if a phone number is valid.
     * 
     * @param phoneNumber The phone number to validate
     * @param defaultRegion Default country code
     * @return true if valid, false otherwise
     */
    fun isValid(phoneNumber: String, defaultRegion: String = DEFAULT_REGION): Boolean {
        return normalize(phoneNumber, defaultRegion) != null
    }
    
    /**
     * Checks if two phone numbers are equal (same E.164 representation).
     * 
     * @param number1 First phone number
     * @param number2 Second phone number
     * @param defaultRegion Default country code
     * @return true if both numbers normalize to the same E.164 format
     * 
     * @example
     * ```
     * areEqual("+1-555-123-4567", "555.123.4567") // Returns: true
     * areEqual("+15551234567", "+15559999999") // Returns: false
     * ```
     */
    fun areEqual(
        number1: String,
        number2: String,
        defaultRegion: String = DEFAULT_REGION
    ): Boolean {
        val norm1 = normalize(number1, defaultRegion)
        val norm2 = normalize(number2, defaultRegion)
        return norm1 != null && norm1 == norm2
    }
    
    /**
     * Formats a phone number for display in national format.
     * 
     * @param phoneNumber The phone number to format
     * @param defaultRegion Default country code
     * @return Formatted number (e.g., "(555) 123-4567") or original if invalid
     * 
     * @example
     * ```
     * formatForDisplay("+15551234567") // Returns: "(555) 123-4567"
     * formatForDisplay("+919876543210", "IN") // Returns: "98765 43210"
     * ```
     */
    fun formatForDisplay(phoneNumber: String, defaultRegion: String = DEFAULT_REGION): String {
        return try {
            val parsed = phoneUtil.parse(phoneNumber, defaultRegion)
            if (phoneUtil.isValidNumber(parsed)) {
                phoneUtil.format(parsed, PhoneNumberUtil.PhoneNumberFormat.NATIONAL)
            } else {
                phoneNumber
            }
        } catch (e: NumberParseException) {
            phoneNumber
        }
    }
    
    /**
     * Formats a phone number for display in international format.
     * 
     * @param phoneNumber The phone number to format
     * @param defaultRegion Default country code
     * @return Formatted number (e.g., "+1 555-123-4567") or original if invalid
     */
    fun formatInternational(phoneNumber: String, defaultRegion: String = DEFAULT_REGION): String {
        return try {
            val parsed = phoneUtil.parse(phoneNumber, defaultRegion)
            if (phoneUtil.isValidNumber(parsed)) {
                phoneUtil.format(parsed, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)
            } else {
                phoneNumber
            }
        } catch (e: NumberParseException) {
            phoneNumber
        }
    }
    
    /**
     * Gets the country code from a phone number.
     * 
     * @param phoneNumber The phone number
     * @param defaultRegion Default country code
     * @return Country calling code (e.g., 1 for US, 91 for India) or null if invalid
     */
    fun getCountryCode(phoneNumber: String, defaultRegion: String = DEFAULT_REGION): Int? {
        return try {
            val parsed = phoneUtil.parse(phoneNumber, defaultRegion)
            if (phoneUtil.isValidNumber(parsed)) {
                parsed.countryCode
            } else {
                null
            }
        } catch (e: NumberParseException) {
            null
        }
    }
}
