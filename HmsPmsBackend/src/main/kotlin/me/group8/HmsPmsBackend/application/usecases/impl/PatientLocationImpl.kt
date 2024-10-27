package me.group8.HmsPmsBackend.application.usecases.impl

import me.group8.HmsPmsBackend.application.dtos.queries.AddressCreateDto
import me.group8.HmsPmsBackend.application.dtos.queries.InfectionDto
import me.group8.HmsPmsBackend.application.usecases.Infection
import me.group8.HmsPmsBackend.application.usecases.PatientLocation
import me.group8.HmsPmsBackend.domain.patient.facades.PatientFacade
import me.group8.HmsPmsBackend.domain.staff.facade.StaffFacade
import org.springframework.stereotype.Service
import java.util.*

@Service
class PatientLocationImpl(
        val patientFacade: PatientFacade,
        val staffFacade: StaffFacade
): PatientLocation {
    override fun addLocationTracking(locationId: String, patientId: String, employeeId: String, startDate: Date, endDate: Date): Boolean {
        val hasPerm = staffFacade.doesHaveUpdatePermission(employeeId)
        if(hasPerm) {
            patientFacade.addLocationTracking(locationId, patientId, startDate, endDate)
            return true
        }
        return false
    }

    override fun addLocation(location: AddressCreateDto, employeeId: String): String? {
        val hasPerm = staffFacade.doesHaveUpdatePermission(employeeId)
        if(hasPerm) {
            return patientFacade.addLocation(location)
        }
        return null
    }

    override fun updateLocation(locationId: String, location: AddressCreateDto, employeeId: String): Boolean {
        val hasPerm = staffFacade.doesHaveUpdatePermission(employeeId)
        if(hasPerm) {
            patientFacade.updateLocation(locationId, location)
            return true
        }
        return false
    }
}