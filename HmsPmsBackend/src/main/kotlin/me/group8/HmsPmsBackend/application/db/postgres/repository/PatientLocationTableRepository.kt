package me.group8.HmsPmsBackend.application.db.postgres.repository

import me.group8.HmsPmsBackend.application.db.postgres.entities.BedEntity
import me.group8.HmsPmsBackend.application.db.postgres.entities.PatientLocationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PatientLocationTableRepository : JpaRepository<PatientLocationEntity, String> {
}