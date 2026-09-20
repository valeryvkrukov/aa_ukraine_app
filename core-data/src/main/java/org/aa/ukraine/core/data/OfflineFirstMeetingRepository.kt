package org.aa.ukraine.core.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.aa.ukraine.core.data.mapper.asEntity
import org.aa.ukraine.core.data.mapper.asExternalModel
import org.aa.ukraine.core.data.util.TestData
import org.aa.ukraine.core.database.dao.MeetingDao
import org.aa.ukraine.core.database.entity.MeetingEntity
import org.aa.ukraine.core.database.model.Meeting
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

class OfflineFirstMeetingRepository @Inject constructor(
    private val meetingDao: MeetingDao
) : MeetingRepository {
    // Read from the local database and convert into Compose models
    override fun getMeetings(): Flow<List<Meeting>> {
        return meetingDao.getAllMeetings().map { entities ->
            entities.map { it.asExternalModel() }
        }
    }

    // Loading simulation: clearing the database and seeding the initial AA Ukraine groups
    override suspend fun syncMeetings() {
        delay(1000.milliseconds)

        val meetingsList: List<Meeting> = TestData.testExternalMeeting
        val entitiesList: List<MeetingEntity> = meetingsList.map { meeting ->
            meeting.asEntity()
        }

        meetingDao.clearAllMeetings()

        meetingDao.insertMeetings(entitiesList)
    }
}