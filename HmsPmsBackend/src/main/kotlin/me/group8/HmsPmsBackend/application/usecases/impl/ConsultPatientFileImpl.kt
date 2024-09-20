package me.group8.HmsPmsBackend.application.usecases.impl

import me.group8.HmsPmsBackend.application.usecases.ConsultPatientFile
import me.group8.HmsPmsBackend.domain.log.facades.LogFacade
import me.group8.HmsPmsBackend.domain.patient.facades.PatientFacade
import me.group8.HmsPmsBackend.domain.patient.entities.Patient
import me.group8.HmsPmsBackend.domain.patient.entities.AdmissionRecord
import me.group8.HmsPmsBackend.domain.medication.facades.MedicationFacade
import me.group8.HmsPmsBackend.domain.medication.entities.Medication
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.Date

@Service
class ConsultPatientFileImpl(
    val patientFacade: PatientFacade, 
    val medicationFacade: MedicationFacade,
    val logFacade: LogFacade
): ConsultPatientFile {
    override fun getPatientFile(patientId: String): Patient? {
        return patientFacade.getPatientInfo(patientId)
        
    }

    override fun getAllPatientAdmission(patientId: String): Array<AdmissionRecord> {
        return patientFacade.getAllPatientAdmission(patientId)
    }

    override fun getAllPatientPrescriptions(patientId: String): Array<Medication> {
        return medicationFacade.getAllPatientPrescriptions(patientId)
    }

    override fun logAccess(employeeId: String, patientId: String) {
        val currentDate = Date()
        logFacade.logAccess(currentDate, employeeId, patientId)
    }

}