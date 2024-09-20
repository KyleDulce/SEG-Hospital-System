package me.group8.HmsPmsBackend.application.usecases.impl

import me.group8.HmsPmsBackend.application.dtos.queries.DivisionBedEntryDto
import me.group8.HmsPmsBackend.application.dtos.queries.DivisionCreateDto
import me.group8.HmsPmsBackend.application.dtos.queries.DivisionEntryDto
import me.group8.HmsPmsBackend.application.dtos.queries.RequestPatientAdmissionCreateDto
import me.group8.HmsPmsBackend.application.usecases.VisualizeDivision
import me.group8.HmsPmsBackend.domain.medicaldivision.entities.AdmitRequest
import me.group8.HmsPmsBackend.domain.medicaldivision.entities.Bed
import me.group8.HmsPmsBackend.domain.medicaldivision.entities.MedicalDivision
import me.group8.HmsPmsBackend.domain.medicaldivision.facades.MedicalDivisionFacade
import me.group8.HmsPmsBackend.domain.staff.facade.StaffFacade
import org.springframework.stereotype.Service

@Service
class VisualizeDivisionImpl(
    val medicalDivisionFacade: MedicalDivisionFacade,
    val staffFacade: StaffFacade
): VisualizeDivision {
    override fun getDivision(divisionId: String): DivisionCreateDto? {
        val div = medicalDivisionFacade.getDivision(divisionId)?: return null
        return divisionToDivisionCreateDto(div)
    }

    override fun getBeds(bedIs: Array<String>): Array<DivisionBedEntryDto> {
        return medicalDivisionFacade.getBeds(bedIs)
            .map { bed -> bedToDto(bed) }
            .toTypedArray()
    }

    override fun getDivisions(): Array<DivisionEntryDto> {
        return medicalDivisionFacade.getDivisions()
            .map { div -> divisionToEntry(div) }
            .toTypedArray()
    }

    override fun getChargeNurseName(chargeNurseId: String): String? {
        return staffFacade.getStaffName(chargeNurseId)
    }

    private fun divisionToDivisionCreateDto(division: MedicalDivision): DivisionCreateDto {
        return DivisionCreateDto(
            division.identifier,
            division.name,
            division.chargeNurseId,
            division.location,
            division.numBeds,
            division.teleExt,
            division.status,
            division.bedIds,
            division.admitReqs
                .map { admitRequest -> admitRequestToDto(admitRequest, division.identifier) }
                .toTypedArray()
        )
    }

    private fun divisionToEntry(division: MedicalDivision): DivisionEntryDto {
        return DivisionEntryDto(
            division.identifier,
            division.name
        )
    }

    private fun admitRequestToDto(admitRequest: AdmitRequest, divisionId: String): RequestPatientAdmissionCreateDto {
        return RequestPatientAdmissionCreateDto(
            divisionId,
            admitRequest.patientId,
            admitRequest.requestingChargeNurseId,
            admitRequest.rationale,
            admitRequest.priority,
                admitRequest.localDoctorId
        )
    }

    private fun bedToDto(bed: Bed): DivisionBedEntryDto {
        return DivisionBedEntryDto(
            bed.roomNum,
            bed.bedNum,
            !bed.isAvailable
        )
    }
}
