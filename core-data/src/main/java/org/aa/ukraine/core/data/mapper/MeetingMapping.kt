package org.aa.ukraine.core.data.mapper

import org.aa.ukraine.core.database.entity.MeetingEntity
import org.aa.ukraine.core.database.model.Meeting
import org.aa.ukraine.core.database.model.MeetingType

// Mapping from the database to a clean UI model
fun MeetingEntity.asExternalModel() = Meeting(
    id = id,
    title = title,
    time = time,
    daysOfWeek = daysOfWeek,
    type = MeetingType.valueOf(type),
    city = city,
    address = address,
    link = link,
    additionalInfo = additionalInfo
)

// Mapping from a pure UI model to a Room database entity
fun Meeting.asEntity(): MeetingEntity {
    return MeetingEntity(
        id = this.id,
        title = this.title,
        time = this.time,
        daysOfWeek = this.daysOfWeek,
        type = this.type.name,
        city = this.city,
        address = this.address,
        link = this.link,
        additionalInfo = this.additionalInfo
    )
}