package com.callblockerpro.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.callblockerpro.app.ui.screens.DashboardScreen
import com.callblockerpro.app.ui.screens.LogsScreen
import com.callblockerpro.app.ui.screens.ListsScreen
import com.callblockerpro.app.ui.screens.SettingsScreen
import com.callblockerpro.app.ui.screens.ScheduleScreen
import com.callblockerpro.app.ui.screens.SplashScreen
import com.callblockerpro.app.ui.screens.OnboardingScreen

import androidx.compose.animation.*
import androidx.compose.animation.core.tween

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // Smart Navigation Helper
    fun navigateTo(route: String) {
        val tabs = listOf("home", "logs", "lists", "settings")
        val isTab = tabs.any { route.startsWith(it) } // simple check

        if (isTab) {
            navController.navigate(route) {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }
        } else {
            navController.navigate(route)
        }
    }

    NavHost(
        navController = navController, 
        startDestination = "splash",
        enterTransition = {
            // Default to Tab Fade for sibling navigation
            fadeIn(animationSpec = tween(300))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(300))
        }
    ) {
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
        composable("home") { DashboardScreen(onNavigate = { navigateTo(it) }) }
        composable("logs") { LogsScreen(onNavigate = { navigateTo(it) }) }
        composable("lists") { ListsScreen(onNavigate = { navigateTo(it) }) }
        composable("settings") { SettingsScreen(onNavigate = { navigateTo(it) }) }
        
        // Modal/Level 2 Screens get the Premium Scale Effect
        composable(
            route = "add?type={type}",
            arguments = listOf(navArgument("type") { defaultValue = 0; type = NavType.IntType }),
            enterTransition = { scaleIn(initialScale = 0.9f, animationSpec = tween(400)) + fadeIn(tween(400)) },
            exitTransition = { scaleOut(targetScale = 1.1f, animationSpec = tween(400)) + fadeOut(tween(400)) },
            popEnterTransition = { scaleIn(initialScale = 1.1f, animationSpec = tween(400)) + fadeIn(tween(400)) },
            popExitTransition = { scaleOut(targetScale = 0.9f, animationSpec = tween(400)) + fadeOut(tween(400)) }
        ) { 
            com.callblockerpro.app.ui.screens.AddScreen(
                onNavigateBack = { navController.popBackStack() }
            ) 
        }
    }
}
