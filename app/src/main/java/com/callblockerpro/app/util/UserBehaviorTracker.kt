package com.callblockerpro.app.util

import android.content.Context
import android.util.Log

/**
 * Mock implementation of UXCam/Session Replay tracking.
 * Logs behavior data to Logcat in a format that simulates session replay capture.
 */
object UserBehaviorTracker {
    private const val TAG = "UserBehaviorTracker"

    fun initialize(context: Context) {
        Log.d(TAG, "Initializing Session Replay Simulator...")
        Log.d(TAG, "Heatmap capture active on all segments.")
        trackEvent("session_started")
    }

    fun trackScreen(name: String) {
        Log.d(TAG, "[SCREEN_VIEW]: $name")
    }

    fun trackEvent(name: String, properties: Map<String, Any> = emptyMap()) {
        Log.d(TAG, "[EVENT]: $name | Data: $properties")
    }

    fun trackRageClick(viewId: String = "unknown") {
        trackEvent("rage_click_detected", mapOf("view_id" to viewId))
    }

    fun trackOnboardingStep(step: Int, name: String) {
        trackEvent("onboarding_step_viewed", mapOf("step" to step, "name" to name))
    }

    fun trackOnboardingCompletion() {
        trackEvent("onboarding_completed")
    }

    fun trackDropOff(screen: String, reason: String) {
        trackEvent("session_drop_off", mapOf("screen" to screen, "reason" to reason))
    }
}
