package me.group8.HmsPmsBackend.domain.medication.factories

import me.group8.HmsPmsBackend.application.dtos.queries.MedicationCreateDto
import me.group8.HmsPmsBackend.domain.medication.entities.Medication
import org.springframework.stereotype.Service

@Service
class MedicationFactory {
    fun createMedication(patientId: String, medicationInfo: MedicationCreateDto): Medication {
        return Medication(
            patientId,
            medicationInfo.drugNumber,
            medicationInfo.drugName,
            medicationInfo.unitsByDay,
            medicationInfo.numAdminPerDay,
            medicationInfo.methodOfAdmin,
            medicationInfo.startDate,
            medicationInfo.endDate,
        )
    }
}
