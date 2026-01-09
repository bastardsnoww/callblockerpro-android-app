package com.callblockerpro.app.util

import android.Manifest
import android.app.Activity
import android.app.role.RoleManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat

/**
 * Utility class for managing call screening permissions and role.
 */
object CallScreeningPermissions {
    
    const val REQUEST_ROLE_CALL_SCREENING = 1001
    
    /**
     * Required permissions for call screening functionality.
     */
    val REQUIRED_PERMISSIONS = buildList {
        add(Manifest.permission.READ_CALL_LOG)
        add(Manifest.permission.ANSWER_PHONE_CALLS)
        // POST_NOTIFICATIONS required on Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            add(Manifest.permission.POST_NOTIFICATIONS)
        }
    }.toTypedArray()
    
    /**
     * Optional permission for contact name resolution.
     */
    const val OPTIONAL_PERMISSION = Manifest.permission.READ_CONTACTS
    
    /**
     * Check if all required permissions are granted.
     */
    fun hasAllPermissions(context: Context): Boolean {
        return REQUIRED_PERMISSIONS.all { permission ->
            ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        }
    }
    
    /**
     * Check if optional permission is granted.
     */
    fun hasContactsPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            OPTIONAL_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED
    }
    
    /**
     * Check if app is set as the default call screening app.
     */
    fun isCallScreeningRoleGranted(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val roleManager = context.getSystemService(RoleManager::class.java)
            return roleManager?.isRoleHeld(RoleManager.ROLE_CALL_SCREENING) == true
        }
        return false
    }
    
    /**
     * Request call screening role.
     * 
     * @return Intent to launch role selection, or null if not supported
     */
    fun createRoleRequestIntent(context: Context): Intent? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val roleManager = context.getSystemService(RoleManager::class.java)
            return roleManager?.createRequestRoleIntent(RoleManager.ROLE_CALL_SCREENING)
        }
        return null
    }
    
    /**
     * Check if all setup requirements are met.
     */
    fun isFullyConfigured(context: Context): Boolean {
        return hasAllPermissions(context) && isCallScreeningRoleGranted(context)
    }
    
    /**
     * Get list of missing permissions.
     */
    fun getMissingPermissions(context: Context): List<String> {
        return REQUIRED_PERMISSIONS.filter { permission ->
            ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED
        }
    }
}
