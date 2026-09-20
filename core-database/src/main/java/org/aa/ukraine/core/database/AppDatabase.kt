package org.aa.ukraine.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.aa.ukraine.core.database.entity.MeetingEntity
import org.aa.ukraine.core.database.dao.MeetingDao
import org.aa.ukraine.core.database.util.Converters

@Database(
    entities = [MeetingEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun meetingDao(): MeetingDao
}
