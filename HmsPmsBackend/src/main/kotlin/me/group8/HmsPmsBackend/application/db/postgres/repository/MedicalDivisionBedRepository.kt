package me.group8.HmsPmsBackend.application.db.postgres.repository

import me.group8.HmsPmsBackend.application.db.postgres.entities.MedicalDivisionBedEntity
import me.group8.HmsPmsBackend.application.db.postgres.entities.id.MedicalDivisionBedId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MedicalDivisionBedRepository: JpaRepository<MedicalDivisionBedEntity, MedicalDivisionBedId> {


    fun findByMedicalDivisionBedId_MedIdentifier(medIdentifier: String): List<MedicalDivisionBedEntity>
}