package org.aa.ukraine.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.aa.ukraine.core.database.entity.MeetingEntity

@Dao
interface MeetingDao {
    // Reading all groups for the general schedule feed
    @Query("SELECT * FROM meetings ORDER BY time ASC")
    fun getAllMeetings(): Flow<List<MeetingEntity>>

    // Saving or updating the schedule
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeetings(meetings: List<MeetingEntity>)

    // Clearing old data before synchronization
    @Query("DELETE FROM meetings")
    suspend fun clearAllMeetings()
}