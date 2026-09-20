package org.aa.ukraine.data

import org.aa.ukraine.core.database.entity.MeetingEntity
import org.aa.ukraine.core.database.model.Meeting
import org.aa.ukraine.core.database.model.MeetingType

object TestData {
    // 1. Mock model for the UI layer
    val testExternalMeeting = Meeting(
        id = "1",
        title = "Единство",
        time = "19:00",
        daysOfWeek = listOf(1, 3, 5), // Mon, Wed, Fri
        type = MeetingType.OFFLINE,
        city = "Харьков",
        address = "ул. Гогля, 4/1",
        link = null,
        additionalInfo = "Вход со двора",
        isSpeakerFormat = true,
        isInCircleOfFriends = false,
        language = "RU"
    )
    // 2. Fake database model for mapping verification
    val testMeetingEntity = MeetingEntity(
        id = "1",
        title = "Феникс",
        time = "19:00",
        daysOfWeek = listOf(1, 3, 5),
        type = "OFFLINE", // It is stored as a String in the database
        city = "Харьков",
        address = "ул. Полтавский Шлях, 10",
        link = null,
        additionalInfo = "Вход со двора"
    )
}