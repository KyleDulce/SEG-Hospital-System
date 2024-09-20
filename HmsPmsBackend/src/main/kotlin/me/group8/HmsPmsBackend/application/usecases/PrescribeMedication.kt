package me.group8.HmsPmsBackend.application.usecases

import me.group8.HmsPmsBackend.application.dtos.queries.MedicationCreateDto
import java.util.*

interface PrescribeMedication {
    fun prescribeMedication(patientId: String, prescription: MedicationCreateDto, requestingDoctorId: String): Boolean
    fun isInCare(patientId: String, requestingDoctorId: String): Boolean
}