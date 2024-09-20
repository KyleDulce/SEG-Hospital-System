package me.group8.HmsPmsBackend.domain.patient.repositories.impl

import me.group8.HmsPmsBackend.application.db.postgres.entities.AdmissionRecordEntity
import me.group8.HmsPmsBackend.application.db.postgres.repository.AdmissionRecordTableRepository
import me.group8.HmsPmsBackend.domain.patient.entities.AdmissionRecord
import me.group8.HmsPmsBackend.domain.patient.repositories.AdmissionRecordRepository
import org.springframework.stereotype.Service

@Service
class AdmissionRecordRepositoryImpl(
    private val admissionRecordTableRepository: AdmissionRecordTableRepository
): AdmissionRecordRepository {
    override fun save(admitRecord: AdmissionRecord): AdmissionRecord {
        admissionRecordTableRepository.save(admitRecordToEntity(admitRecord))
        return admitRecord
    }

    override fun find(admitRecordId: String): AdmissionRecord? {
        val admitRecordOpt = admissionRecordTableRepository.findById(admitRecordId)

        if(admitRecordOpt.isEmpty) {
            return null
        }
        return entityToAdmitRecord(admitRecordOpt.get())
    }

    override fun findByPatientId(patientId: String): AdmissionRecord? {
        val admitRecords = admissionRecordTableRepository.findByPatientId(patientId)
            .filter { admitRecord -> !admitRecord.isDischarged }

        if(admitRecords.isEmpty()) {
            return null
        }
        return entityToAdmitRecord(admitRecords[0])
    }

    override fun findAllByPatientId(patientId: String): Array<AdmissionRecord> {
        val admitRecords = admissionRecordTableRepository.findByPatientId(patientId)

        return admitRecords
            .map { admitRecord -> entityToAdmitRecord(admitRecord) }
            .toTypedArray()
    }

    private fun admitRecordToEntity(admitRecord: AdmissionRecord): AdmissionRecordEntity {
        return AdmissionRecordEntity(
            admitRecord.recordId,
            admitRecord.patientId,
            admitRecord.localDoctor,
            admitRecord.divisionId,
            admitRecord.roomNum,
            admitRecord.bedNumber,
            admitRecord.privInsuranceNum,
            admitRecord.isDischarged
        )
    }

    private fun entityToAdmitRecord(entity: AdmissionRecordEntity): AdmissionRecord {
        return AdmissionRecord(
            entity.recordId,
            entity.patientId,
            entity.localDoctorId,
            entity.divisionId,
            entity.roomNumber,
            entity.bedNumber,
            entity.privInsuranceNum,
            entity.isDischarged
        )
    }
}