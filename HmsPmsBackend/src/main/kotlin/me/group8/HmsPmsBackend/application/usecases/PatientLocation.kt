package me.group8.HmsPmsBackend.application.usecases

import me.group8.HmsPmsBackend.application.dtos.queries.AddressCreateDto
import java.util.Date

interface PatientLocation {
    fun addLocationTracking(locationId: String, patientId: String, employeeId: String, startDate: Date, endDate: Date): Boolean
    fun addLocation(location: AddressCreateDto, employeeId: String): String?
    fun updateLocation(locationId: String, location: AddressCreateDto, employeeId: String): Boolean
}