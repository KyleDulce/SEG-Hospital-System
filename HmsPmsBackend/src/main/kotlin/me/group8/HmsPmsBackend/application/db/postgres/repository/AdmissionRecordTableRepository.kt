package me.group8.HmsPmsBackend.application.db.postgres.repository

import me.group8.HmsPmsBackend.application.db.postgres.entities.AdmissionRecordEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AdmissionRecordTableRepository: JpaRepository<AdmissionRecordEntity, String> {
    fun findByPatientId(patientId: String): List<AdmissionRecordEntity>
}