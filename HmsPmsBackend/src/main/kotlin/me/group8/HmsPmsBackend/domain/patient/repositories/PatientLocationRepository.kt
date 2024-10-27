package me.group8.HmsPmsBackend.domain.patient.repositories

import me.group8.HmsPmsBackend.domain.patient.entities.Location
import me.group8.HmsPmsBackend.domain.patient.entities.LocationTracking
import java.util.*

interface PatientLocationRepository {
    fun save(location: Location): Location
    fun find(locationId: String): Location?
    fun findAllByPatientId(patientId: String): Array<Location?>
    fun saveLocationTracking(locationId: String, patientId: String, startDate: Date, endDate: Date)
    fun findPatientIdsByLocationId(locationId: String?): Array<String>
    fun findLocationTrackingByLocationId(locationId: String?): Array<LocationTracking>
    fun findLocationTracking(locationId: String?, patientId: String?): LocationTracking?
}