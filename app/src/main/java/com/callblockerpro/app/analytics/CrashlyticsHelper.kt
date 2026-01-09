package com.callblockerpro.app.analytics

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CrashlyticsHelper @Inject constructor() {
    fun recordException(t: Throwable) {
        // No-op
    }
    
    fun setCustomKey(key: String, value: Any) {
        // No-op
    }
    
    fun log(message: String) {
        // No-op
    }
    
    fun setCrashlyticsCollectionEnabled(enabled: Boolean) {
        // No-op
    }
    
    fun logBackupError(action: String, t: Throwable) {}
}
