package org.aa.ukraine.feature.schedule.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

const val scheduleRoute = "schedule_route"

/**
 * An extension function for screen registration in the main NavHost (in the :app module).
 *
 * @param content A slot for the screen itself. We pass it via a lambda
 * to decouple the `MainScreen()` initialization call from the `:app` module.
 */
@Suppress("unused")
fun NavGraphBuilder.scheduleScreen(
    //nestedGraphs: NavGraphBuilder.() -> Unit = {}, ???
    content: @Composable () -> Unit
) {
    composable(route = scheduleRoute) {
        content()
    }
}

/**
 * Function for external calls (e.g., when clicking the "Go to schedule" button)
 */
fun NavHostController.navigateToSchedule() {
    this.navigate(scheduleRoute) {
        // Clear the back stack if navigating to the main screen permanently
        launchSingleTop = true
    }
}