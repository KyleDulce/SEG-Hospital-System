package me.group8.HmsPmsBackend.application.db.postgres.entities

import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import me.group8.HmsPmsBackend.application.db.postgres.entities.id.LocationTrackingId
import me.group8.HmsPmsBackend.utils.TypeUtils

@Entity
@Table(name = "location_tracking")
class LocationTrackingEntity (
        @EmbeddedId
        var locationTrackingId: LocationTrackingId,
        @Column(name = "location_start_date")
        var startDate: java.util.Date,
        @Column(name = "location_end_date")
        var endDate: java.util.Date
) {
    constructor() : this(LocationTrackingId(), TypeUtils.blankSqlDate(), TypeUtils.blankSqlDate()) {

    }
}