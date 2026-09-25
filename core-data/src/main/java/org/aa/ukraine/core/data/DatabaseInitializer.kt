package org.aa.ukraine.core.data

import android.util.Log
import dagger.Lazy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import org.aa.ukraine.core.AppInitializer
import org.aa.ukraine.core.database.AppDatabase
import org.aa.ukraine.core.database.entity.MeetingEntity
import javax.inject.Inject

class DatabaseInitializer @Inject constructor(
    private val appDatabase: Lazy<AppDatabase>,
) : AppInitializer {
    override suspend fun init(): Unit = withContext(Dispatchers.IO) {
        try {
            yield()

            val db = appDatabase.get()
            val meetingDao = db.meetingDao()
            val hasData = meetingDao.hasMeetings()

            if (!hasData) {
                val defaultEntities = org.aa.ukraine.core.data.util.TestData.testExternalMeetings.map { meeting ->
                    MeetingEntity(
                        id = meeting.id,
                        title = meeting.title,
                        time = meeting.time,
                        daysOfWeek = meeting.daysOfWeek,
                        type = meeting.type.name,
                        city = meeting.city,
                        address = meeting.address,
                        link = meeting.link,
                        additionalInfo = meeting.additionalInfo
                    )
                }

                yield()

                meetingDao.insertMeetings(defaultEntities)
            }
        } catch (e: Exception) {
            Log.e("DatabaseInitializer", "Database initialization error: ${e.localizedMessage}")
        }
    }
}
