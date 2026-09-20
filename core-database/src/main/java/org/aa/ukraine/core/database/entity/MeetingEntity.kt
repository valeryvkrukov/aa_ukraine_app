package org.aa.ukraine.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meetings")
data class MeetingEntity (
    @PrimaryKey
    val id: String,
    val title: String,
    val time: String,
    val daysOfWeek: List<Int>, // It will be processed via Converters.kt
    val type: String,          // Stored as a String ("ONLINE" / "OFFLINE")
    val city: String?,
    val address: String?,
    val link: String?,
    val additionalInfo: String?
)