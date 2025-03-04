package com.bloodspy.calendar.data.local.eventWithNotification

import androidx.room.Embedded
import androidx.room.Relation
import com.bloodspy.calendar.data.local.event.EventLocal
import com.bloodspy.calendar.data.local.notification.NotificationLocal

data class EventWithNotifications(
    @Embedded val event: EventLocal,
    @Relation(
        parentColumn = "id",
        entityColumn = "eventId"
    ) val notifications: List<NotificationLocal>,
)
