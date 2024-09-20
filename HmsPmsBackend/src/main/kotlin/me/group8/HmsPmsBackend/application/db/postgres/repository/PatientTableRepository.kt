package me.group8.HmsPmsBackend.application.db.postgres.repository

import me.group8.HmsPmsBackend.application.db.postgres.entities.PatientEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PatientTableRepository: JpaRepository<PatientEntity, String> {
}