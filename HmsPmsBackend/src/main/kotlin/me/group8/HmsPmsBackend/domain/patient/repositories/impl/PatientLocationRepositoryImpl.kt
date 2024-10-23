package me.group8.HmsPmsBackend.domain.patient.repositories.impl

import jakarta.persistence.Column
import me.group8.HmsPmsBackend.application.db.postgres.entities.LocationTrackingEntity
import me.group8.HmsPmsBackend.application.db.postgres.entities.MedicationEntity
import me.group8.HmsPmsBackend.application.db.postgres.entities.PatientLocationEntity
import me.group8.HmsPmsBackend.application.db.postgres.entities.id.LocationTrackingId
import me.group8.HmsPmsBackend.application.db.postgres.repository.LocationTrackingTableRepository
import me.group8.HmsPmsBackend.application.db.postgres.repository.PatientLocationTableRepository
import me.group8.HmsPmsBackend.domain.patient.entities.Location
import me.group8.HmsPmsBackend.domain.patient.repositories.PatientLocationRepository
import org.springframework.stereotype.Service

@Service
class PatientLocationRepositoryImpl (
        private val patientLocationTableRepository: PatientLocationTableRepository,
        private val locationTrackingRepository: LocationTrackingTableRepository,
): PatientLocationRepository {
    override fun save(location: Location): Location {
        patientLocationTableRepository.save(locationToEntity(location))
        return location
    }

    override fun find(locationId: String): Location? {
        val locationOpt = patientLocationTableRepository.findById(locationId)

        if(locationOpt.isEmpty) {
            return null
        }
        return entityToLocation(locationOpt.get())
    }

    override fun saveLocationTracking(locationId: String, patientId: String) {
        val locationTrackingId = LocationTrackingId(patientId, locationId)

        // check that entity does not exist before adding it
        if ( locationTrackingRepository.findById(locationTrackingId).isEmpty) {
            val locationTracking = LocationTrackingEntity(locationTrackingId)
            locationTrackingRepository.save(locationTracking)
        }
    }

    private fun locationToEntity(location: Location): PatientLocationEntity {
        return PatientLocationEntity(
                location.locationId,
                location.streetNum,
                location.streetName,
                location.aptNumber,
                location.postalCode,
                location.city,
                location.province,
                location.country
        )
    }

    private fun entityToLocation(entity: PatientLocationEntity): Location {
        return Location(
                entity.id,
                entity.streetNum,
                entity.streetName,
                entity.aptNumber,
                entity.postalCode,
                entity.city,
                entity.province,
                entity.country
        )
    }

    override fun findAllByPatientId(patientId: String): Array<Location?> {
        val locationIds = locationTrackingRepository.findByLocationTrackingId_PatientId(patientId)

        return locationIds.map { locationId ->
            this.find(locationId.locationTrackingId.locationId)
        }.toTypedArray()
    }

    override fun findPatientIdsByLocationId(locationId: String?): Array<String> {
        if (locationId == null)
            return arrayOf()
        val locationIds = locationTrackingRepository.findByLocationTrackingId_LocationId(locationId)

        return locationIds.map { locationId ->
            locationId.locationTrackingId.patientId
        }.toTypedArray()
    }
}