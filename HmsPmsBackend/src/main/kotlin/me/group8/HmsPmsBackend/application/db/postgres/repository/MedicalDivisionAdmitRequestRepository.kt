package me.group8.HmsPmsBackend.application.db.postgres.repository

import me.group8.HmsPmsBackend.application.db.postgres.entities.MedicalDivisionAdmitRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MedicalDivisionAdmitRequestRepository : JpaRepository<MedicalDivisionAdmitRequest, String> {


    fun findByMedIddentifier(medIddentifier: String): List<MedicalDivisionAdmitRequest>

}