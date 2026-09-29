package org.aa.ukraine.feature.schedule.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import org.aa.ukraine.feature.schedule.ui.ScheduleScreen

fun EntryProviderScope<NavKey>.scheduleEntryProvider(backStack: NavBackStack<NavKey>) {
    entry<ScheduleKey> {
        ScheduleScreen()
    }
}
