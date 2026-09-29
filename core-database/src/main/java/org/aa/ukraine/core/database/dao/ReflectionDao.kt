package org.aa.ukraine.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.aa.ukraine.core.database.entity.ReflectionEntity

@Dao
interface ReflectionDao {
    /**
     * Returns a reactive stream of reflections for a specific day and locale.
     * Automatically updates the UI when changes occur in the database.
     */
    @Query("SELECT * FROM daily_reflections WHERE date = :date AND languageCode = :lang LIMIT 1")
    fun getReflectionStream(date: String, lang: String): Flow<ReflectionEntity?>

    /**
     * A direct request to the data layer (Repository) to check for the presence of a cache.
     */
    @Query("SELECT * FROM daily_reflections WHERE date = :date AND languageCode = :lang LIMIT 1")
    suspend fun getReflectionDirect(date: String, lang: String): ReflectionEntity?

    /**
     * Atomically saves the generated Gemini response to the cache.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReflection(reflection: ReflectionEntity)
}