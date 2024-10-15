package me.group8.HmsPmsBackend.domain.patient.entities

import java.util.Date

class Infection (
        val infectionId: String,
        val patientId: String,
        val startDate: Date,
        val endDate: Date,
        val name: String
) {
}