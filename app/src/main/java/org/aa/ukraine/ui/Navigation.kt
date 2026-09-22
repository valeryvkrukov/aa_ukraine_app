package org.aa.ukraine.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import org.aa.ukraine.core.ui.screen.AnimatedSplashScreen
import org.aa.ukraine.feature.main.ui.screen.MainScreen
import org.aa.ukraine.feature.schedule.ui.screen.ScheduleScreen

sealed interface Screen {
    @Serializable
    data object Splash : Screen

    @Serializable
    data object Main : Screen

    @Serializable
    data object Schedule : Screen
}

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash, // Start strictly from the Splash screen
    ) {
        // 1. Animated splash screen
        composable<Screen.Splash> {
            AnimatedSplashScreen {
                navController.navigate(Screen.Main) {
                    popUpTo(Screen.Splash) { inclusive = true }
                }
            }
        }

        // 2. The main screen of the application
        composable<Screen.Main> {
            MainScreen(
                onScheduleClick = { navController.navigate(Screen.Schedule) }
            )
        }

        // 3. Schedule screen
        composable<Screen.Schedule> {
            ScheduleScreen()
        }
    }
}
