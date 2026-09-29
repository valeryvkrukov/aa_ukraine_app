package org.aa.ukraine.feature.map.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import org.aa.ukraine.feature.map.ui.MapScreen

fun EntryProviderScope<NavKey>.mapEntryProvider(backStack: NavBackStack<NavKey>) {
    entry<MapKey> {
        MapScreen()
    }
}
