package me.group8.HmsPmsBackend.domain.patient.entities

import me.group8.HmsPmsBackend.application.dtos.queries.InfectionDto
import java.util.Date

class Infection (
        val infectionId: String,
        var patientId: String,
        var startDate: Date,
        var endDate: Date,
        var name: String
) {
    fun updateInfo(updateInfo: InfectionDto) {
        this.patientId = updateInfo.patientId
        this.startDate = updateInfo.startDate
        this.endDate = updateInfo.endDate
        this.name = updateInfo.name
    }
}