package me.group8.HmsPmsBackend.domain.medicaldivision.repositories

import me.group8.HmsPmsBackend.domain.medicaldivision.entities.AdmitRequest
import me.group8.HmsPmsBackend.domain.medicaldivision.entities.MedicalDivision

interface MedicalDivisionRepository {
    fun find(divisionId: String): MedicalDivision?
    fun save(divisionId: MedicalDivision): MedicalDivision
    fun getAll(): Array<MedicalDivision>
    fun findByChargeNurseId(chargeNurseId: String): MedicalDivision?
    fun findAdmitRequestDivisionByPatientId(patientId: String): String?
}