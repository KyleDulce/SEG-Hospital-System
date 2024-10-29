package me.group8.HmsPmsBackend.application.controller.payload

import me.group8.HmsPmsBackend.domain.patient.entities.Location

data class LocationResponse(
    val location: Location?,
    val startDate: String,
    val endDate: String
)