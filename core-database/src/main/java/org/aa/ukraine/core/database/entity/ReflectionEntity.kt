package org.aa.ukraine.core.database.entity

import androidx.room.Entity

@Entity(
    tableName = "daily_reflections",
    primaryKeys = ["date", "languageCode"]
)
data class ReflectionEntity(
    val date: String,          // Format ISO: YYYY-MM-DD
    val languageCode: String,  // ISO codes: "uk", "en", "ru"
    val title: String,         // Topic for reflection of the day
    val quote: String,         // Quote from Book AA
    val quoteSource: String,   // Source (e.g., "Alcoholics Anonymous, p. 46")
    val commentary: String     // Analysis and reflections from Gemini
)
