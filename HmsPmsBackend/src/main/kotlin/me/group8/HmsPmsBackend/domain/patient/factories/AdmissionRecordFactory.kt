package me.group8.HmsPmsBackend.domain.patient.factories

import me.group8.HmsPmsBackend.domain.patient.entities.AdmissionRecord
import java.util.*
import org.springframework.stereotype.Service

@Service
class AdmissionRecordFactory {
    fun create(divisionId: String,
               roomNum: Int,
               bedNum: Int,
               patientId: String,
               privInsuranceNum: Long,
               localDoctorId: String): AdmissionRecord {
        return AdmissionRecord(
            UUID.randomUUID().toString(),
            patientId,
            localDoctorId,
            divisionId,
            roomNum,
            bedNum,
            privInsuranceNum
        )
    }
}