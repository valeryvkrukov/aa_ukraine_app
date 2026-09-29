package org.aa.ukraine.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import org.aa.ukraine.core.database.dao.ReflectionDao
import org.aa.ukraine.core.database.entity.ReflectionEntity

@Database(entities = [Main::class, ReflectionEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mainDao(): MainDao

    abstract fun reflectionDao(): ReflectionDao
}
