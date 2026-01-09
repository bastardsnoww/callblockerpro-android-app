package com.callblockerpro.app.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings

/**
 * Helper class for managing battery optimization settings.
 * 
 * Battery optimization can prevent the CallScreeningService from running reliably,
 * especially on devices with aggressive power management (e.g., FuntouchOS, MIUI).
 */
object BatteryOptimizationHelper {
    
    /**
     * Check if the app is exempted from battery optimization.
     */
    fun isIgnoringBatteryOptimizations(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val powerManager = context.getSystemService(Context.POWER_SERVICE) as? PowerManager
            return powerManager?.isIgnoringBatteryOptimizations(context.packageName) == true
        }
        return true // Not applicable on older versions
    }
    
    /**
     * Create an intent to request battery optimization exemption.
     * 
     * @return Intent to launch battery optimization settings, or null if not supported
     */
    fun createBatteryOptimizationIntent(context: Context): Intent? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
                data = Uri.parse("package:${context.packageName}")
            }
        }
        return null
    }
    
    /**
     * Create an intent to open battery optimization settings page.
     * Useful as a fallback if the direct request doesn't work.
     */
    fun createBatteryOptimizationSettingsIntent(): Intent {
        return Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)
    }
    
    /**
     * Check if battery optimization request is needed.
     */
    fun shouldRequestBatteryOptimization(context: Context): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && 
               !isIgnoringBatteryOptimizations(context)
    }
}
