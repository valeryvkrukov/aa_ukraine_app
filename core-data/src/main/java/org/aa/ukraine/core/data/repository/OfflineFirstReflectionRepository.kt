package org.aa.ukraine.core.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.aa.ukraine.core.database.dao.ReflectionDao
import org.aa.ukraine.core.database.entity.ReflectionEntity
import org.aa.ukraine.core.network.AiReflectionClient
import org.aa.ukraine.core.network.model.NetworkReflection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfflineFirstReflectionRepository @Inject constructor(
    private val aiClient: AiReflectionClient,
    private val reflectionDao: ReflectionDao
) : ReflectionRepository {
    override fun getDailyReflectionStream(date: String, langCode: String): Flow<NetworkReflection?> {
        return reflectionDao.getReflectionStream(date, langCode).map { entity ->
            entity?.let {
                NetworkReflection(
                    title = it.title,
                    quote = it.quote,
                    quoteSource = it.quoteSource,
                    commentary = it.commentary
                )
            }
        }
    }

    override suspend fun syncReflection(date: String, langCode: String): Boolean = withContext(
        Dispatchers.IO
    ) {
        try {
            // Checking; the cache might have already appeared.
            val existing = reflectionDao.getReflectionDirect(date, langCode)
            if (existing != null) return@withContext true

            // Connecting to the remote Gemini 3 Flash model via Firebase AI Logic
            val networkData = aiClient.fetchDailyReflection(date, langCode)

            // Perform an atomic write to the local SQLite database.
            reflectionDao.insertReflection(
                ReflectionEntity(
                    date = date,
                    languageCode = langCode,
                    title = networkData.title,
                    quote = networkData.quote,
                    quoteSource = networkData.quoteSource,
                    commentary = networkData.commentary
                )
            )
            true
        } catch (e: Exception) {
            android.util.Log.e("ReflectionRepository", "Gemini synchronization error: ${e.localizedMessage}")
            false
        }
    }
}