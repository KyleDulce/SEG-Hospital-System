package me.group8.HmsPmsBackend.domain.medication.facades

import me.group8.HmsPmsBackend.application.dtos.queries.MedicationCreateDto
import me.group8.HmsPmsBackend.domain.medication.entities.Medication

interface MedicationFacade {
    fun createPrescription(patientId: String, medicationInfo: MedicationCreateDto): Boolean
    fun getAllPatientPrescriptions(patientId: String): Array<Medication>

}