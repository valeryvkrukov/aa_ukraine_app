package org.aa.ukraine.core.data.util

import org.aa.ukraine.core.database.model.Meeting
import org.aa.ukraine.core.database.model.MeetingType

object TestData {
    // 1. Mock model for the UI layer
    val testExternalMeetings: List<Meeting> = listOf(
        Meeting(
            id = "1",
            title = "Единство",
            time = "19:00",
            daysOfWeek = listOf(1, 3, 5), // Mon, Wed, Fri
            type = MeetingType.OFFLINE,
            city = "Харьков",
            address = "ул. Гоголя, 4/1",
            link = null,
            additionalInfo = "Вход со двора",
            isSpeakerFormat = true,
            isInCircleOfFriends = false,
            language = "RU"
        ),
        Meeting(
            id = "1",
            title = "Свобода (Онлайн)",
            time = "20:00",
            daysOfWeek = listOf(2, 4, 6, 7),
            type = MeetingType.ONLINE,
            city = null,
            address = null,
            link = "https://zoom.us",
            additionalInfo = "Пароль: 123",
            isInCircleOfFriends = true
        )
    )
}