package me.group8.HmsPmsBackend.application.db.postgres.repository

import me.group8.HmsPmsBackend.application.db.postgres.entities.InfectionEntity
import me.group8.HmsPmsBackend.application.db.postgres.entities.MedicationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InfectionTableRepository: JpaRepository<InfectionEntity, String> {
    fun findByPatientId(patientId: String): List<InfectionEntity>
}