package org.aa.ukraine.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.aa.ukraine.core.ui.screen.AnimatedSplashScreen
import org.aa.ukraine.feature.main.ui.screen.MainScreen

object Screen {
    const val SPLASH = "splash_screen"
    const val MAIN = "main_screen"
}

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.SPLASH, // Start strictly from the Splash screen
    ) {
        // 1. Animated splash screen
        composable(route = Screen.SPLASH) {
            AnimatedSplashScreen {
                navController.navigate(Screen.MAIN) {
                    popUpTo(Screen.SPLASH) { inclusive = true }
                }
            }
        }

        // 2. The main screen of the application
        composable(route = Screen.MAIN) {
            MainScreen()
        }
    }
}
