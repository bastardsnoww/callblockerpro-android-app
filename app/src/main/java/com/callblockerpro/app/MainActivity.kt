package com.callblockerpro.app

import android.os.Bundle
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import com.callblockerpro.app.util.UserBehaviorTracker

@AndroidEntryPoint
class MainActivity : androidx.activity.ComponentActivity() {

    @javax.inject.Inject
    lateinit var userPreferencesRepository: com.callblockerpro.app.data.repository.UserPreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize Behavior Tracker (Mock for Session Replay & Heatmaps)
        UserBehaviorTracker.initialize(this)

        setContent {
            com.callblockerpro.app.ui.theme.CallBlockerProTheme {
                com.callblockerpro.app.ui.navigation.AppNavigation()
            }
        }
    }
}
