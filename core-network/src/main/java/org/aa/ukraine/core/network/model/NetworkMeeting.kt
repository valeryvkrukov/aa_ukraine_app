package org.aa.ukraine.core.network.model

/**
 * Meeting format: ONLINE (Zoom/Skype) or OFFLINE (in-person meetings)
 */
enum class NetworkMeetingType {
    ONLINE, OFFLINE
}

/**
 * Clean AA group network model for parsing via Jsoup
 */
data class NetworkMeeting(
    val title: String,
    val time: String,
    val daysOfWeek: List<Int>, // 1 (Mon) .. 7 (Sun)
    val type: NetworkMeetingType,
    val city: String?,
    val address: String?,
    val link: String?,
    val additionalInfo: String?
)
