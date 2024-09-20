package me.group8.HmsPmsBackend.domain.patient.repositories

import me.group8.HmsPmsBackend.domain.patient.entities.AdmissionRecord

interface AdmissionRecordRepository {
    fun save(admitRecord: AdmissionRecord): AdmissionRecord
    fun find(admitRecordId: String): AdmissionRecord?
    fun findByPatientId(patientId: String): AdmissionRecord?
    fun findAllByPatientId(patientId: String): Array<AdmissionRecord>
}