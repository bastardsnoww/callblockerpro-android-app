package com.callblockerpro.app.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build

/**
 * Helper class for detecting OEM-specific devices and providing guidance.
 * 
 * Some OEMs (Xiaomi, Vivo, Oppo, Huawei) have aggressive battery optimization
 * that can prevent call screening from working reliably.
 */
object OemHelper {
    
    /**
     * Detect if the device is from a manufacturer known for aggressive battery optimization.
     */
    fun isAggressiveOem(): Boolean {
        val manufacturer = Build.MANUFACTURER.lowercase()
        return manufacturer in listOf(
            "xiaomi", "redmi", "poco",
            "vivo", "iqoo",
            "oppo", "realme", "oneplus",
            "huawei", "honor",
            "samsung" // Samsung also has some restrictions
        )
    }
    
    /**
     * Get the OEM name for display.
     */
    fun getOemName(): String {
        return Build.MANUFACTURER.replaceFirstChar { it.uppercase() }
    }
    
    /**
     * Check if this is a Vivo/iQOO device (FuntouchOS).
     */
    fun isFuntouchOS(): Boolean {
        val manufacturer = Build.MANUFACTURER.lowercase()
        return manufacturer == "vivo" || manufacturer == "iqoo"
    }
    
    /**
     * Check if this is a Xiaomi device (MIUI).
     */
    fun isMIUI(): Boolean {
        val manufacturer = Build.MANUFACTURER.lowercase()
        return manufacturer in listOf("xiaomi", "redmi", "poco")
    }
    
    /**
     * Check if this is an Oppo/Realme device (ColorOS).
     */
    fun isColorOS(): Boolean {
        val manufacturer = Build.MANUFACTURER.lowercase()
        return manufacturer in listOf("oppo", "realme", "oneplus")
    }
    
    /**
     * Get auto-start settings intent for the specific OEM.
     * Returns null if not supported or unknown OEM.
     */
    fun getAutoStartIntent(context: Context): Intent? {
        return try {
            when {
                isFuntouchOS() -> {
                    // Vivo/iQOO auto-start settings
                    Intent().apply {
                        setClassName(
                            "com.vivo.permissionmanager",
                            "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"
                        )
                    }
                }
                isMIUI() -> {
                    // Xiaomi auto-start settings
                    Intent().apply {
                        setClassName(
                            "com.miui.securitycenter",
                            "com.miui.permcenter.autostart.AutoStartManagementActivity"
                        )
                    }
                }
                isColorOS() -> {
                    // Oppo/Realme auto-start settings
                    Intent().apply {
                        setClassName(
                            "com.coloros.safecenter",
                            "com.coloros.safecenter.permission.startup.StartupAppListActivity"
                        )
                    }
                }
                else -> null
            }
        } catch (e: Exception) {
            null
        }
    }
    
    /**
     * Get user-friendly instructions for enabling auto-start.
     */
    fun getAutoStartInstructions(): String {
        return when {
            isFuntouchOS() -> """
                To ensure CallBlockerPro works reliably:
                
                1. Go to Settings → Battery → Background power consumption management
                2. Find CallBlockerPro in the list
                3. Enable "Allow background activity"
                
                Also enable Auto-start:
                1. Go to Settings → Apps → Autostart
                2. Find CallBlockerPro
                3. Toggle it ON
            """.trimIndent()
            
            isMIUI() -> """
                To ensure CallBlockerPro works reliably:
                
                1. Go to Settings → Apps → Manage apps
                2. Find CallBlockerPro
                3. Enable "Autostart"
                4. Under "Battery saver", select "No restrictions"
            """.trimIndent()
            
            isColorOS() -> """
                To ensure CallBlockerPro works reliably:
                
                1. Go to Settings → Battery → App Battery Management
                2. Find CallBlockerPro
                3. Disable battery optimization
                
                Also enable Auto-launch:
                1. Go to Settings → Privacy → Permission manager → Auto-launch
                2. Find CallBlockerPro and enable it
            """.trimIndent()
            
            else -> """
                To ensure CallBlockerPro works reliably, please:
                
                1. Disable battery optimization for this app
                2. Allow background activity
                3. Enable auto-start if available in your device settings
            """.trimIndent()
        }
    }
}
