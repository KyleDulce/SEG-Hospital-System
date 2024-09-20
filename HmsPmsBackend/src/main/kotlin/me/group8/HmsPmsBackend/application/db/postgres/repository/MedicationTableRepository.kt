package me.group8.HmsPmsBackend.application.db.postgres.repository

import me.group8.HmsPmsBackend.application.db.postgres.entities.MedicationEntity
import me.group8.HmsPmsBackend.application.db.postgres.entities.id.MedicationId
import me.group8.HmsPmsBackend.domain.medication.entities.Medication
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.Query; 
import org.springframework.data.repository.query.Param; 

@Repository
interface MedicationTableRepository: JpaRepository<MedicationEntity, MedicationId> {
    fun findByMedicationIdPatientId(patientId: String): List<MedicationEntity>

}