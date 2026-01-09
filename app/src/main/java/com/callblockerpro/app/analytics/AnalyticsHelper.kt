package com.callblockerpro.app.analytics

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnalyticsHelper @Inject constructor() {
    fun logEvent(event: String, params: Any? = null) {
        // No-op for size optimization
    }
    
    fun setUserProperty(key: String, value: String) {
        // No-op
    }

    fun logThemeChanged(mode: String) {}
    fun logBackupCreated(location: String, isAuto: Boolean, isEncrypted: Boolean) {}
    fun logBackupRestored(location: String, isSuccess: Boolean) {}
    fun logGoogleSignIn(success: Boolean, message: String? = null) {} // Added this too just in case
    fun logGoogleSignOut() {} // And this as well
}
