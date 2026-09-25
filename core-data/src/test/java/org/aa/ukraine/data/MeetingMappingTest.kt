package org.aa.ukraine.data

import org.aa.ukraine.core.data.mapper.asEntity
import org.aa.ukraine.core.data.mapper.asExternalModel
import org.aa.ukraine.core.data.util.TestData
import org.junit.Assert.assertEquals
import org.junit.Test

class MeetingMappingTest {
    @Test
    fun meetingEntity_canBeMapped_toExternalModel() {
        val originalModel = TestData.testExternalMeetings.first()
        val entity = originalModel.asEntity()
        val externalModel = entity.asExternalModel()

        assertEquals(entity.id, externalModel.id)
        assertEquals(entity.title, externalModel.title)
        assertEquals(entity.time, externalModel.time)
        assertEquals(entity.daysOfWeek, externalModel.daysOfWeek)
        assertEquals(entity.city, externalModel.city)
        assertEquals(entity.address, externalModel.address)
    }

    @Test
    fun externalMeeting_canBeMapped_toDatabaseEntity() {
        val externalModel = TestData.testExternalMeetings.last()
        val entity = externalModel.asEntity()

        assertEquals(externalModel.id, entity.id)
        assertEquals(externalModel.title, entity.title)
        assertEquals(externalModel.type.name, entity.type)
    }
}