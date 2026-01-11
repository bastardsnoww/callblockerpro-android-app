package com.callblockerpro.app

import android.os.Bundle
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : androidx.activity.ComponentActivity() {

    @javax.inject.Inject
    lateinit var userPreferencesRepository: com.callblockerpro.app.data.repository.UserPreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Manual Edge-to-Edge for compatibility
        androidx.core.view.WindowCompat.setDecorFitsSystemWindows(window, false)
        
        setContent {
            com.callblockerpro.app.ui.theme.CallBlockerProTheme {
                com.callblockerpro.app.ui.navigation.AppNavigation()
            }
        }
    }
}
