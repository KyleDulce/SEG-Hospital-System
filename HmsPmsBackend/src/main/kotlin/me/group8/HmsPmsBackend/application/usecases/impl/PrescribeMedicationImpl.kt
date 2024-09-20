package me.group8.HmsPmsBackend.application.usecases.impl

import me.group8.HmsPmsBackend.application.usecases.PrescribeMedication
import me.group8.HmsPmsBackend.domain.medication.facades.MedicationFacade
import me.group8.HmsPmsBackend.application.dtos.queries.MedicationCreateDto
import me.group8.HmsPmsBackend.domain.patient.facades.PatientFacade
import org.springframework.stereotype.Service

// PrescribeMedicationImpl.kt
@Service
class PrescribeMedicationImpl(
   val medicationFacade: MedicationFacade,
   val patientFacade: PatientFacade
):PrescribeMedication
 {
    override fun prescribeMedication(patientId: String, prescription: MedicationCreateDto, requestingDoctorId: String): Boolean {
        val isInCare = patientFacade.isPatientInCareOfDoctor(patientId, requestingDoctorId)
        if(isInCare) {
            return medicationFacade.createPrescription(patientId, prescription)
        }
        return false
    }

     override fun isInCare(patientId: String, requestingDoctorId: String): Boolean {
         return patientFacade.isPatientInCareOfDoctor(patientId, requestingDoctorId)
     }
 }