package me.group8.HmsPmsBackend.application.usecases

import me.group8.HmsPmsBackend.domain.patient.entities.Patient
import me.group8.HmsPmsBackend.domain.patient.entities.AdmissionRecord
import me.group8.HmsPmsBackend.domain.medication.entities.Medication


interface ConsultPatientFile {
    fun getPatientFile(patientId: String): Patient?
    fun getAllPatientAdmission(patientId: String): Array<AdmissionRecord>
    fun getAllPatientPrescriptions(patientId: String): Array<Medication>

    fun logAccess(employeeId: String, patientId: String)
}