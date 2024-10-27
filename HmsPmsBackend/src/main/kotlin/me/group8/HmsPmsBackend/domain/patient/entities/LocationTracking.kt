package me.group8.HmsPmsBackend.domain.patient.entities;

import java.util.Date

class LocationTracking (
        val locationId: String,
        val patientId: String,
        var startDate: Date,
        var endDate: Date
) {
}
