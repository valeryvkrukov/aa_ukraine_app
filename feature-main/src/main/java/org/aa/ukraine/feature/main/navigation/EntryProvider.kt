package org.aa.ukraine.feature.main.navigation

import org.aa.ukraine.feature.main.ui.screen.MainScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

@Composable
fun EntryProviderScope<NavKey>.MainEntryProvider(backStack: NavBackStack<NavKey>) {
    entry<Main> {
        MainScreen(
            //onItemClick = { navKey -> backStack.add(navKey) },
            modifier = Modifier.padding(16.dp)
        )
    }
}
