package me.group8.HmsPmsBackend.application.usecases.impl

import me.group8.HmsPmsBackend.application.dtos.queries.RequestPatientAdmissionCreateDto
import me.group8.HmsPmsBackend.application.usecases.RequestPatientAdmission
import me.group8.HmsPmsBackend.domain.medicaldivision.facades.MedicalDivisionFacade
import me.group8.HmsPmsBackend.domain.patient.facades.PatientFacade
import org.springframework.stereotype.Service

@Service
class RequestPatientAdmissionImpl(
    val medicalDivisionFacade: MedicalDivisionFacade,
    val patientFacade: PatientFacade
): RequestPatientAdmission {
    override fun requestPatientAdmission(requestAdmission: RequestPatientAdmissionCreateDto): Boolean {
        val onList = medicalDivisionFacade.isPatientInRequestList(requestAdmission.divisionId,
            requestAdmission.patientId)

        val inDivision = patientFacade.isPatientInDivision(requestAdmission.patientId)

        if(!onList && !inDivision) {
            return medicalDivisionFacade.addToRequestlist(
                requestAdmission.divisionId,
                requestAdmission.patientId,
                requestAdmission.requestRationale,
                requestAdmission.priority,
                requestAdmission.requestingChargeNurse,
                    requestAdmission.localDoctorID
            )
        }
        return false
    }
}