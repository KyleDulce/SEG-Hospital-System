package me.group8.HmsPmsBackend.teststubs.repositories

import me.group8.HmsPmsBackend.domain.patient.entities.AdmissionRecord
import me.group8.HmsPmsBackend.domain.patient.repositories.AdmissionRecordRepository

class AdmissionRecordRepositoryStub: AdmissionRecordRepository {
    val recordIdAdmissionMap: MutableMap<String, AdmissionRecord> = HashMap()

    override fun save(admitRecord: AdmissionRecord): AdmissionRecord {
        recordIdAdmissionMap[admitRecord.recordId] = admitRecord
        return admitRecord;
    }

    override fun find(admitRecordId: String): AdmissionRecord? {
        return recordIdAdmissionMap[admitRecordId]
    }

    override fun findByPatientId(patientId: String): AdmissionRecord? {
        for(admitRecord: AdmissionRecord in recordIdAdmissionMap.values) {
            if(admitRecord.patientId.equals(patientId)) {
                return admitRecord
            }
        }
        return null
    }

    override fun findAllByPatientId(patientId: String): Array<AdmissionRecord> {
        val records = arrayListOf<AdmissionRecord>()
        for(admitRecord: AdmissionRecord in recordIdAdmissionMap.values) {
            if(admitRecord.patientId.equals(patientId)) {
                records.add(admitRecord)
            }
        }
        return records.toTypedArray()
    }
}