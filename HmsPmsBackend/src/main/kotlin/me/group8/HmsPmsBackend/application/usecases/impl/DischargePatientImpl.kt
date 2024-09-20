package me.group8.HmsPmsBackend.application.usecases.impl

import me.group8.HmsPmsBackend.application.dtos.queries.DischargePatientCreateDto
import me.group8.HmsPmsBackend.application.usecases.DischargePatient
import me.group8.HmsPmsBackend.domain.medicaldivision.facades.MedicalDivisionFacade
import me.group8.HmsPmsBackend.domain.patient.entities.AdmissionRecord
import me.group8.HmsPmsBackend.domain.patient.facades.PatientFacade
import org.springframework.stereotype.Service

@Service
class DischargePatientImpl(
    val patientFacade: PatientFacade,
    val medicalDivisionFacade: MedicalDivisionFacade
): DischargePatient {

    override fun dischargePatient(dischargePatient: DischargePatientCreateDto): Boolean {
        val admitRecord = patientFacade.getCurrentPatientAdmission(dischargePatient.patientId)
        if (admitRecord != null) {
            medicalDivisionFacade.dischargePatient(admitRecord.bedNumber, admitRecord.roomNum)
            patientFacade.dischargePatientAndNotify(dischargePatient.patientId)
            return true
        }
        return false
    }

    override fun getAdmissionInfo(patientId: String): AdmissionRecord? {
        return patientFacade.getCurrentPatientAdmission(patientId);
    }

}