package me.group8.HmsPmsBackend.application.db.postgres.entities

import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import me.group8.HmsPmsBackend.application.db.postgres.entities.id.LocationTrackingId

@Entity
@Table(name = "location_tracking")
class LocationTrackingEntity (
        @EmbeddedId
        var locationTrackingId: LocationTrackingId
) {
    constructor() : this(LocationTrackingId()) {

    }
}