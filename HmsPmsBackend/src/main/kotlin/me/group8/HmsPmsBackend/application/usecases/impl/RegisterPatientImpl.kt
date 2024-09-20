package me.group8.HmsPmsBackend.application.usecases.impl

import me.group8.HmsPmsBackend.application.dtos.queries.PatientCreateDto
import me.group8.HmsPmsBackend.application.usecases.RegisterPatient
import me.group8.HmsPmsBackend.domain.patient.facades.PatientFacade
import org.springframework.stereotype.Service

@Service
class RegisterPatientImpl(
    val patientFacade: PatientFacade
): RegisterPatient {
    override fun registerPatient(patientData: PatientCreateDto): Boolean {
        return patientFacade.registerPatient(patientData)
    }
}