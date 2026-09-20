package org.aa.ukraine.core.database.model

/**
 * Format of AA community meetings.
 */
enum class MeetingType {
    ONLINE,
    OFFLINE
}

/**
 * A clean business model for the Ukraine AA group.
 * It is free from Room/DB annotations and is used exclusively in the UI layer.
 */
data class Meeting(
    val id: String,                  // Unique meeting ID
    val title: String,               // Group name (e.g., "Unity", "Beginning")
    val time: String,                // Time of the event (e.g. 19:00)
    val daysOfWeek: List<Int>,       // Days of the week: 1 (Mon) .. 7 (Sun)
    val type: MeetingType,           // Format: ONLINE or OFFLINE
    val city: String?,               // City (for in-person meetings, e.g., "Kharkiv")
    val address: String?,            // Exact address (for in-person meetings)
    val link: String?,               // Link (for online meetings: Zoom/Skype/Telegram)
    val additionalInfo: String?,     // Additional notes (e.g., "Entrance from the courtyard")

    // Additional markers for which we have implemented UI chips:
    val isSpeakerFormat: Boolean = false,     // Speakers' meeting
    val isInCircleOfFriends: Boolean = false, // "Among Friends" format
    val language: String = "UA"               // Language of the meeting (default: Ukrainian)
)