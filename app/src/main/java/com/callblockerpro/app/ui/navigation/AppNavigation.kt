package com.callblockerpro.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.callblockerpro.app.ui.screens.DashboardScreen
import com.callblockerpro.app.ui.screens.LogsScreen
import com.callblockerpro.app.ui.screens.ListsScreen
import com.callblockerpro.app.ui.screens.SettingsScreen
import com.callblockerpro.app.ui.screens.ScheduleScreen
import com.callblockerpro.app.ui.screens.SplashScreen
import com.callblockerpro.app.ui.screens.OnboardingScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(
                onNavigateToHome = {
                    navController.navigate("home") {
                        popUpTo("splash") { inclusive = true }
                    }
                },
                onNavigateToOnboarding = {
                    navController.navigate("onboarding") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }
        composable("onboarding") {
            OnboardingScreen(
                onOnboardingFinished = {
                    navController.navigate("home") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                }
            )
        }
        composable("home") { DashboardScreen(onNavigate = { navController.navigate(it) }) }
        composable("logs") { LogsScreen { navController.navigate(it) } }
        composable("lists") { ListsScreen(onNavigate = { navController.navigate(it) }) }
        composable("settings") { SettingsScreen(onNavigate = { navController.navigate(it) }) }
        composable("add") { 
            com.callblockerpro.app.ui.screens.AddScreen(
                onNavigateBack = { navController.popBackStack() }
            ) 
        }
    }
}
