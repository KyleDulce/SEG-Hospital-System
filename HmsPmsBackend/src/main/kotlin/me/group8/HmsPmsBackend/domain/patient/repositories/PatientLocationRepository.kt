package me.group8.HmsPmsBackend.domain.patient.repositories

import me.group8.HmsPmsBackend.domain.patient.entities.Location

interface PatientLocationRepository {
    fun save(location: Location): Location
    fun find(locationId: String): Location?
    fun findAllByPatientId(patientId: String): Array<Location?>
    fun saveLocationTracking(locationId: String, patientId: String)
}