package org.aa.ukraine.feature.schedule.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import org.aa.ukraine.core.data.MeetingRepository
import org.aa.ukraine.core.database.model.Meeting
import javax.inject.Inject
import kotlin.collections.emptyMap


enum class DisplayType {
    SIMPLE_LIST,
    GROUPED_DAYS
}

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    meetingRepository: MeetingRepository
) : ViewModel() {
    // Local thread for tracking the user's selected display mode
    private val _displayType = MutableStateFlow(DisplayType.SIMPLE_LIST)

    // Combine the thread of meetings from the repository and the display mode thread
    val uiState: StateFlow<ScheduleScreenUiState> = meetingRepository.getMeetings()
        .combine<List<Meeting>, DisplayType, ScheduleScreenUiState>(_displayType) { meetings, displayType ->
            when (displayType) {
                DisplayType.SIMPLE_LIST -> {
                    ScheduleScreenUiState.Success(
                        meetings = meetings,
                        groupedMeetings = emptyMap<Int, List<Meeting>>(),
                        displayType = displayType
                    )
                }
                DisplayType.GROUPED_DAYS -> {
                    ScheduleScreenUiState.Success(
                        meetings = emptyList<Meeting>(),
                        groupedMeetings = groupMeetingsByDay(meetings),
                        displayType = displayType
                    )
                }
            }
        }
        .catch { throwable ->
            emit(ScheduleScreenUiState.Error(throwable))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ScheduleScreenUiState.Loading
        )

    /**
     * Changing the display type (invoked from UI panels)
     */
    fun setDisplayType(type: DisplayType) {
        _displayType.value = type
    }

    /**
     * Grouping the schedule by day of the week (1–7)
     */
    private fun groupMeetingsByDay(meetings: List<Meeting>): Map<Int, List<Meeting>> {
        val resultMap = mutableMapOf<Int, MutableList<Meeting>>()
        for (day in 1..7) { resultMap[day] = mutableListOf() }

        for (meeting in meetings) {
            for (day in meeting.daysOfWeek) {
                resultMap[day]?.add(meeting)
            }
        }
        return resultMap.mapValues { (_, list) -> list.sortedBy { it.time } }
    }
}

/**
 * Schedule screen UI states
 */
sealed interface ScheduleScreenUiState {
    object Loading : ScheduleScreenUiState
    data class Error(val throwable: Throwable) : ScheduleScreenUiState
    data class Success(
        val meetings: List<Meeting>,
        val groupedMeetings: Map<Int, List<Meeting>>,
        val displayType: DisplayType
    ) : ScheduleScreenUiState
}