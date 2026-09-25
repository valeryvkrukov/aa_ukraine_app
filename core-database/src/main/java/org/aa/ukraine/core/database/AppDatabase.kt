package org.aa.ukraine.core.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Main::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mainDao(): MainDao
}
