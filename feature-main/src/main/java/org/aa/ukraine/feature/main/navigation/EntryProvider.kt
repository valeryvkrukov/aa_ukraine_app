package org.aa.ukraine.feature.main.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import org.aa.ukraine.feature.main.ui.screen.MainScreen

@Composable
fun EntryProviderScope<NavKey>.MainEntryProvider(backStack: NavBackStack<NavKey>) {
    entry<Main> {
        MainScreen(
            onScheduleClick = {}
        )
    }
}
