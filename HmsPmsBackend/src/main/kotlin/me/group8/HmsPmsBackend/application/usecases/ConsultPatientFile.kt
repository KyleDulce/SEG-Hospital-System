package me.group8.HmsPmsBackend.application.usecases

import me.group8.HmsPmsBackend.domain.medication.entities.Medication
import me.group8.HmsPmsBackend.domain.patient.entities.*
import me.group8.HmsPmsBackend.domain.patient.entities.Infection


interface ConsultPatientFile {
    fun getPatientFile(patientId: String): Patient?
    fun getAllPatientAdmission(patientId: String): Array<AdmissionRecord>
    fun getAllPatientPrescriptions(patientId: String): Array<Medication>
    fun getAllPatientInfections(patientId: String): Array<Infection>
    fun getPatientInfectionStatus(patientId: String): InfectionStatus
    fun getAllPatientLocations(patientId: String): Array<Location?>

    fun logAccess(employeeId: String, patientId: String)
}