package me.group8.HmsPmsBackend.application.usecases.impl

import me.group8.HmsPmsBackend.application.dtos.queries.PatientCreateDto
import me.group8.HmsPmsBackend.application.usecases.UpdatePatientFile
import me.group8.HmsPmsBackend.domain.patient.facades.PatientFacade
import me.group8.HmsPmsBackend.domain.staff.facade.StaffFacade
import org.springframework.stereotype.Service
import java.util.*
@Service
class UpdatePatientFileImpl(
    val patientFacade: PatientFacade,
    val staffFacade: StaffFacade
): UpdatePatientFile {
    override fun updatePatientFile(patientId: String, employeeId: String, patientInfoUpdated: PatientCreateDto): Boolean {
        val hasPerm = staffFacade.doesHaveUpdatePermission(employeeId)
        if(hasPerm) {
            patientFacade.updatePatient(patientId, patientInfoUpdated)
            return true
        }
        return false
    }
}