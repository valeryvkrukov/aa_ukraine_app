package org.aa.ukraine.core.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.aa.ukraine.core.data.mapper.asEntity
import org.aa.ukraine.core.data.mapper.asExternalModel
import org.aa.ukraine.core.data.util.TestData
import org.aa.ukraine.core.database.dao.MeetingDao
import org.aa.ukraine.core.database.model.Meeting
import javax.inject.Inject

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
        val mockData = TestData.testExternalMeetings

        meetingDao.clearAllMeetings()

        val entities = mockData.map { it.asEntity() }

        meetingDao.insertMeetings(entities)
    }
}