package me.group8.HmsPmsBackend.application.controller.payload

data class InfectionRequest (
        val patientId: String,
        val startDate: String,
        val endDate: String,
        val name: String
)