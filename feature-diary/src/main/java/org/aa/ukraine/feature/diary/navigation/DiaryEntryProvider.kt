package org.aa.ukraine.feature.diary.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import org.aa.ukraine.feature.diary.ui.DiaryScreen

fun EntryProviderScope<NavKey>.diaryEntryProvider(backStack: NavBackStack<NavKey>) {
    entry<DiaryKey> {
        DiaryScreen()
    }
}
