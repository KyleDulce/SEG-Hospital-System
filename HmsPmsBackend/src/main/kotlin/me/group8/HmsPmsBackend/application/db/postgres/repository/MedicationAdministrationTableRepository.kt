package me.group8.HmsPmsBackend.application.db.postgres.repository

import me.group8.HmsPmsBackend.application.db.postgres.entities.MedicationAdminEntity
import me.group8.HmsPmsBackend.application.db.postgres.entities.id.MedicationAdminId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MedicationAdministrationTableRepository: JpaRepository<MedicationAdminEntity, MedicationAdminId> {
}