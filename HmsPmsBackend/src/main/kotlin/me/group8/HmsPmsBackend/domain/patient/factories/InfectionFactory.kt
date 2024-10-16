package me.group8.HmsPmsBackend.domain.patient.factories

import me.group8.HmsPmsBackend.application.dtos.queries.InfectionDto
import me.group8.HmsPmsBackend.application.dtos.queries.MedicationCreateDto
import me.group8.HmsPmsBackend.domain.medication.entities.Medication
import me.group8.HmsPmsBackend.domain.patient.entities.Infection
import org.springframework.stereotype.Service
import java.util.*

@Service
class InfectionFactory {
    fun createInfection(infectionInfo: InfectionDto): Infection {
        return Infection(
                UUID.randomUUID().toString(),
                infectionInfo.patientId,
                infectionInfo.startDate,
                infectionInfo.endDate,
                infectionInfo.name
        )
    }
}