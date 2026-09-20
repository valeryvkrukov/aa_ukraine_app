package org.aa.ukraine.core.database.util

import androidx.room.TypeConverter

class Converters {
    // Convert the list of days [1, 3, 5] into the string "1,3,5" for storage in an SQLite table
    @TypeConverter
    fun fromIntList(value: List<Int>?): String? {
        if (value == null) return null

        return value.joinToString(separator = ",")
    }

    // Split the string "1,3,5" back into a full-fledged `List<Int>` when reading from the database
    @TypeConverter
    fun toIntList(value: String?): List<Int> {
        if (value.isNullOrEmpty()) return emptyList()
        return value.split(",").mapNotNull { stringElement ->
            stringElement.trim().toIntOrNull()
        }
    }
}