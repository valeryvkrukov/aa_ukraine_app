package org.aa.ukraine.core.data.repository

import kotlinx.coroutines.flow.Flow
import org.aa.ukraine.core.network.model.NetworkReflection

interface ReflectionRepository {
    /**
     * Returns a reactive stream of reflections for a specific day and language.
     */
    fun getDailyReflectionStream(date: String, langCode: String): Flow<NetworkReflection?>

    /**
     * Forced background synchronization with Gemini 3 Flash if the cache is empty.
     */
    suspend fun syncReflection(date: String, langCode: String): Boolean
}