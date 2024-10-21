package me.group8.HmsPmsBackend.application.db.postgres.repository

import me.group8.HmsPmsBackend.application.db.postgres.entities.LocationTrackingEntity
import me.group8.HmsPmsBackend.application.db.postgres.entities.id.LocationTrackingId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LocationTrackingTableRepository: JpaRepository<LocationTrackingEntity, LocationTrackingId> {


    fun findByLocationTrackingId_PatientId(patientId: String): List<LocationTrackingEntity>
}