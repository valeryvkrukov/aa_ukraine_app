package org.aa.ukraine.feature.assistant.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import org.aa.ukraine.feature.assistant.ui.AssistantScreen

fun EntryProviderScope<NavKey>.assistantEntryProvider(backStack: NavBackStack<NavKey>) {
    entry<AssistantKey> {
        AssistantScreen()
    }
}
