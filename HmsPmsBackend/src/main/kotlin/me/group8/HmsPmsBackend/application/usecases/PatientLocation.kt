package me.group8.HmsPmsBackend.application.usecases

import me.group8.HmsPmsBackend.application.dtos.queries.AddressCreateDto

interface PatientLocation {
    fun addLocationTracking(locationId: String, patientId: String, employeeId: String): Boolean
    fun addLocation(location: AddressCreateDto, employeeId: String): String?
    fun updateLocation(locationId: String, infection: AddressCreateDto, employeeId: String): Boolean
}