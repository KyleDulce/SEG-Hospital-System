package me.group8.HmsPmsBackend.application.db.postgres.entities.id

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
class LocationTrackingId(
        @Column(name = "patient_id")
        var patientId: String,
        @Column(name = "location_id")
        var locationId: String
): Serializable {
    constructor() : this("", "") {

    }
}