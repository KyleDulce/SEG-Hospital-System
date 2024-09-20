package me.group8.HmsPmsBackend.application.db.postgres.repository

import me.group8.HmsPmsBackend.application.db.postgres.entities.MedicalDivisionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MedicalDivisionTableRepository: JpaRepository<MedicalDivisionEntity, String> {

    fun findByChargeNurseId(chargeNurseId: String): Optional<MedicalDivisionEntity>

}