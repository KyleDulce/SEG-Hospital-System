package me.group8.HmsPmsBackend.application.usecases.impl

import me.group8.HmsPmsBackend.application.dtos.queries.AdmitPatientCreateDto
import me.group8.HmsPmsBackend.application.usecases.AdmitPatient
import me.group8.HmsPmsBackend.domain.medicaldivision.facades.MedicalDivisionFacade
import me.group8.HmsPmsBackend.domain.patient.facades.PatientFacade
import org.springframework.stereotype.Service

@Service
class AdmitPatientImpl(
        val medicalDivisionFacade: MedicalDivisionFacade,
        val patientFacade: PatientFacade
) : AdmitPatient {
    override fun admitPatient(patientAdmission: AdmitPatientCreateDto): Boolean {
        val isComplete: Boolean? = medicalDivisionFacade.isDivisionComplete(patientAdmission.divisionId)
        if(isComplete == null || isComplete == true){
            return false;
        }

        val patientCurrentAdmission = patientFacade.getCurrentPatientAdmission(patientAdmission.patientId);
        if(patientCurrentAdmission != null) {
            return false;
        }

        val result = medicalDivisionFacade.admitPatient(patientAdmission.bedNum,patientAdmission.roomNum, patientAdmission.divisionId) &&
                patientFacade.admitPatient(patientAdmission.divisionId,
                                                patientAdmission.roomNum,
                                                patientAdmission.bedNum,
                                                patientAdmission.patientId,
                                                patientAdmission.privateInsuranceNum,
                                                patientAdmission.localDoctorId)

        val onList = medicalDivisionFacade.isPatientInRequestList(patientAdmission.patientId)

        if (onList != null && result) {
            medicalDivisionFacade.removePatientFromRequestList(onList,
                patientAdmission.patientId)
        }

        return result;
    }

    override fun getNurseDivisionId(chargeNurseId: String): String? {
        return medicalDivisionFacade.getDivisionIdByChargeNurse(chargeNurseId)
    }
}