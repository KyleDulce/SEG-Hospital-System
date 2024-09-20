package me.group8.HmsPmsBackend.application.usecases

import me.group8.HmsPmsBackend.application.dtos.queries.DischargePatientCreateDto
import me.group8.HmsPmsBackend.domain.patient.entities.AdmissionRecord
import java.util.*

interface DischargePatient {
    fun dischargePatient(dischargePatient: DischargePatientCreateDto): Boolean
    fun getAdmissionInfo(patientId: String): AdmissionRecord?
}