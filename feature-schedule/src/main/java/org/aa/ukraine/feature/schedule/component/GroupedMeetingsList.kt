package org.aa.ukraine.feature.schedule.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.aa.ukraine.core.database.model.Meeting
import org.aa.ukraine.core.ui.component.MeetingCard
import org.aa.ukraine.feature.schedule.R

/**
 * Grouping by day of the week using built-in sticky headers
 */
@Composable
fun GroupedMeetingsList(groupedMeetings: Map<Int, List<Meeting>>) {
    val dayNames = mapOf(
        1 to stringResource(R.string.day_mon),
        2 to stringResource(R.string.day_tue),
        3 to stringResource(R.string.day_wed),
        4 to stringResource(R.string.day_thu),
        5 to stringResource(R.string.day_fri),
        6 to stringResource(R.string.day_sat),
        7 to stringResource(R.string.day_sun)
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        groupedMeetings.forEach { (dayInt, meetings) ->
            // Show the day of the week only if it contains at least one group
            if (meetings.isNotEmpty()) {
                stickyHeader {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)) // Прозрачный синий подзаголовок дня
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = dayNames[dayInt] ?: "",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }

                items(meetings, key = { "${dayInt}_${it.id}" }) { meeting ->
                    MeetingCard(meeting = meeting, onActionClick = { /* Action on click (Zoom/Map) */ })
                }
            }
        }
    }
}