package com.callblockerpro.app

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.callblockerpro.app.analytics.AnalyticsHelper
import com.callblockerpro.app.analytics.CrashlyticsHelper

import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class CallBlockerApplication : Application(), Configuration.Provider {

    @Inject lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        
        // Initialize crash handler
        com.callblockerpro.app.util.CrashHandler.init(this)
        
        // Analytics and Crashlytics disabled for lightweight version
    }
}
