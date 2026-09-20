package org.aa.ukraine.core.data

import kotlinx.coroutines.flow.Flow
import org.aa.ukraine.core.database.model.Meeting

interface MeetingRepository {
    // Obtaining a data stream for display in UI lists
    fun getMeetings(): Flow<List<Meeting>>

    // Method for future data synchronization with the aa.org.ua website
    suspend fun syncMeetings()
}