package org.aa.ukraine.feature.main.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import org.aa.ukraine.feature.main.ui.MainScreen

fun EntryProviderScope<NavKey>.mainEntryProvider(backStack: NavBackStack<NavKey>) {
    entry<MainKey> {
        MainScreen()
    }
}
