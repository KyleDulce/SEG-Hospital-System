package me.group8.HmsPmsBackend.application.usecases.impl

import me.group8.HmsPmsBackend.application.dtos.queries.AdmitPatientCreateDto
import me.group8.HmsPmsBackend.application.usecases.AdmitPatient
import me.group8.HmsPmsBackend.application.usecases.AdmitPatientFromRequestList
import me.group8.HmsPmsBackend.domain.medicaldivision.facades.MedicalDivisionFacade
import me.group8.HmsPmsBackend.domain.staff.facade.StaffFacade
import org.springframework.stereotype.Service

@Service
class AdmitPatientFromRequestListImpl(
    val medicalDivisionFacade: MedicalDivisionFacade,
    val staffFacade: StaffFacade,
    val admitPatientUseCase: AdmitPatient
) : AdmitPatientFromRequestList {

    override fun admitPatientFromRequestList(admitPatientCreateDto: AdmitPatientCreateDto): Boolean {
        val onList = medicalDivisionFacade.isPatientInRequestList(admitPatientCreateDto.divisionId,
                                                                admitPatientCreateDto.patientId)
        if (onList) {
            return admitPatientUseCase.admitPatient(admitPatientCreateDto)
        }
        return false
    }

    override fun rejectPatientAdmission(divisionId: String, patientId: String) {
        val chargeNurseId = medicalDivisionFacade.getChargeNurseForRequest(divisionId, patientId)
        if (chargeNurseId != null) {
            medicalDivisionFacade.removePatientFromRequestList(divisionId, patientId)
            staffFacade.notifyStaffRejectedAdmission(chargeNurseId, patientId)
        }
    }
}