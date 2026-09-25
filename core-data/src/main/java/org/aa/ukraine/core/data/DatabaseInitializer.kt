package org.aa.ukraine.core.data

import android.util.Log
import dagger.Lazy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.aa.ukraine.core.AppInitializer
import org.aa.ukraine.core.database.AppDatabase
import javax.inject.Inject

class DatabaseInitializer @Inject constructor(
    private val appDatabase: Lazy<AppDatabase>,
) : AppInitializer {
    override suspend fun init(): Unit = withContext(Dispatchers.IO) {
        try {
            val db = appDatabase.get()
            val meetingDao = db.meetingDao()
            val hasData = meetingDao.hasMeetings()

            if (!hasData) {
                // TODO
            }
        } catch (e: Exception) {
            Log.e("DatabaseInitializer", "Database initialization error: ${e.localizedMessage}")
        }
    }
}
