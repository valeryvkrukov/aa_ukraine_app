package org.aa.ukraine.ui

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import org.aa.ukraine.core.ui.LucideIcons
import org.aa.ukraine.core.ui.R as CoreR
import org.aa.ukraine.core.ui.component.AppBottomBar
import org.aa.ukraine.core.ui.component.AppTopBar
import org.aa.ukraine.feature.assistant.navigation.AssistantKey
import org.aa.ukraine.feature.assistant.navigation.assistantEntryProvider
import org.aa.ukraine.feature.diary.navigation.DiaryKey
import org.aa.ukraine.feature.diary.navigation.diaryEntryProvider
import org.aa.ukraine.feature.main.navigation.MainKey
import org.aa.ukraine.feature.main.navigation.mainEntryProvider
import org.aa.ukraine.feature.map.navigation.MapKey
import org.aa.ukraine.feature.map.navigation.mapEntryProvider
import org.aa.ukraine.feature.schedule.navigation.ScheduleKey
import org.aa.ukraine.feature.schedule.navigation.scheduleEntryProvider

private data class TopLevelDestination(
    val key: NavKey,
    val titleRes: Int,
    val icon: ImageVector,
)

@Composable
fun MainNavigation() {
    val backStack: NavBackStack<NavKey> = rememberNavBackStack(MainKey)
    val currentKey = backStack.lastOrNull() ?: MainKey

    val topLevelDestinations = listOf(
        TopLevelDestination(MainKey, CoreR.string.nav_main, LucideIcons.Home),
        TopLevelDestination(MapKey, CoreR.string.nav_map, LucideIcons.Map),
        TopLevelDestination(ScheduleKey, CoreR.string.nav_schedule, LucideIcons.CalendarDays),
        TopLevelDestination(DiaryKey, CoreR.string.nav_diary, LucideIcons.NotebookPen),
        TopLevelDestination(AssistantKey, CoreR.string.nav_assistant, LucideIcons.CircleHelp),
    )

    val title = when (currentKey) {
        MapKey -> stringResource(CoreR.string.screen_map)
        ScheduleKey -> stringResource(CoreR.string.screen_schedule)
        DiaryKey -> stringResource(CoreR.string.screen_diary)
        AssistantKey -> stringResource(CoreR.string.screen_assistant)
        else -> stringResource(CoreR.string.main_top_bar_title)
    }

    Scaffold(
        topBar = {
            AppTopBar(title = title)
        },
        bottomBar = {
            AppBottomBar {
                NavigationBar(
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.onSecondary,
                ) {
                    topLevelDestinations.forEach { destination ->
                        val destinationTitle = stringResource(destination.titleRes)
                        CompositionLocalProvider(LocalIndication provides ripple(color = Color.Transparent)) {
                            NavigationBarItem(
                                selected = currentKey == destination.key,
                                onClick = {
                                    if (currentKey != destination.key) {
                                        backStack.clear()
                                        backStack.add(destination.key)
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector = destination.icon,
                                        contentDescription = destinationTitle,
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = Color.Transparent,
                                    selectedIconColor = MaterialTheme.colorScheme.primary,
                                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                ),
                            )
                        }
                    }
                }
            }
        },
    ) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            modifier = Modifier.padding(innerPadding),
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
            entryProvider = entryProvider {
                mainEntryProvider(backStack = backStack)
                mapEntryProvider(backStack = backStack)
                scheduleEntryProvider(backStack = backStack)
                diaryEntryProvider(backStack = backStack)
                assistantEntryProvider(backStack = backStack)
            },
        )
    }
}
