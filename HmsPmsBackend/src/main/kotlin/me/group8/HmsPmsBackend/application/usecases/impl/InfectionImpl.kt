package me.group8.HmsPmsBackend.application.usecases.impl

import me.group8.HmsPmsBackend.application.dtos.queries.InfectionDto
import me.group8.HmsPmsBackend.application.usecases.Infection
import me.group8.HmsPmsBackend.domain.patient.facades.PatientFacade
import me.group8.HmsPmsBackend.domain.staff.facade.StaffFacade
import org.springframework.stereotype.Service

@Service
class InfectionImpl(
        val patientFacade: PatientFacade,
        val staffFacade: StaffFacade
): Infection {
    override fun addInfection(infection: InfectionDto, employeeId: String): Boolean {
        val hasPerm = staffFacade.doesHaveUpdatePermission(employeeId)
        if(hasPerm) {
            patientFacade.addInfection(infection)
            return true
        }
        return false
    }

    override fun updateInfection(infectionId: String, infection: InfectionDto, employeeId: String): Boolean {
        val hasPerm = staffFacade.doesHaveUpdatePermission(employeeId)
        if(hasPerm) {
            patientFacade.updateInfection(infectionId, infection)
            return true
        }
        return false
    }
}