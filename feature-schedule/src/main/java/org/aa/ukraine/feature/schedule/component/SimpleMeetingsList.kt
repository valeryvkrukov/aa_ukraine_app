package org.aa.ukraine.feature.schedule.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.aa.ukraine.core.database.model.Meeting
import org.aa.ukraine.core.ui.component.MeetingCard

/**
 * A simple flat strip of cards
 */
@Composable
fun SimpleMeetingsList(meetings: List<Meeting>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(meetings, key = { it.id }) { meeting ->
            MeetingCard(meeting = meeting, onActionClick = { /* Action on click (Zoom/Map) */ })
        }
    }
}