package org.aa.ukraine.feature.schedule.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import org.aa.ukraine.core.ui.component.AppBottomBar
import org.aa.ukraine.core.ui.component.AppTopBar
import org.aa.ukraine.feature.schedule.component.GroupedMeetingsList
import org.aa.ukraine.feature.schedule.component.SimpleMeetingsList
import org.aa.ukraine.feature.schedule.ui.viewmodel.DisplayType
import org.aa.ukraine.feature.schedule.ui.viewmodel.ScheduleScreenUiState
import org.aa.ukraine.feature.schedule.ui.viewmodel.ScheduleViewModel

@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier,
    viewModel: ScheduleViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            val currentDisplayType = (uiState as? ScheduleScreenUiState.Success)?.displayType ?: DisplayType.SIMPLE_LIST

            AppTopBar(
                title = "Расписание АА",
                navigationIcon = {
                    IconButton(onClick = { /* Hamburger menu opening logic */ }) {
                        Icon(Icons.Default.Menu, contentDescription = "Меню")
                    }
                },
                actions = {
                    // Display mode toggle button
                    if (currentDisplayType == DisplayType.SIMPLE_LIST) {
                        IconButton(onClick = { viewModel.setDisplayType(DisplayType.GROUPED_DAYS) }) {
                            Icon(Icons.Default.CalendarToday, contentDescription = "По дням недели")
                        }
                    } else {
                        IconButton(onClick = { viewModel.setDisplayType(DisplayType.SIMPLE_LIST) }) {
                            Icon(Icons.AutoMirrored.Filled.ViewList, contentDescription = "Линейный список")
                        }
                    }
                }
            )
        },
        bottomBar = {
            AppBottomBar {
                // Auxiliary actions from below, if needed in the future
                Text(
                    text = "Анонимные Алкоголики Украины",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp),
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (val state = uiState) {
                is ScheduleScreenUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is ScheduleScreenUiState.Error -> {
                    Text(
                        text = "Ошибка загрузки: ${state.throwable.localizedMessage}",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center).padding(16.dp)
                    )
                }
                is ScheduleScreenUiState.Success -> {
                    if (state.displayType == DisplayType.SIMPLE_LIST) {
                        SimpleMeetingsList(meetings = state.meetings)
                    } else {
                        GroupedMeetingsList(groupedMeetings = state.groupedMeetings)
                    }
                }
            }
        }
    }
}