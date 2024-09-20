package me.group8.HmsPmsBackend.domain.patient.repositories.impl

import me.group8.HmsPmsBackend.application.db.postgres.entities.AdmissionRecordEntity
import me.group8.HmsPmsBackend.application.db.postgres.repository.AdmissionRecordTableRepository
import me.group8.HmsPmsBackend.domain.patient.entities.AdmissionRecord
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@SpringBootTest
@Transactional
class AdmissionRecordRepositoryImplIntegrationTest {
    @Autowired
    lateinit var admissionRecordRepositoryImpl: AdmissionRecordRepositoryImpl

    @Autowired
    lateinit var admissionRecordTableRepository: AdmissionRecordTableRepository

    @Test
    fun testSave_success() {
        val admitRecordEntity = AdmissionRecordEntity(
            "72287047-8021-4c87-9a61-a62db3b26599",
            "5ffaec5a-7eb5-42f9-a0f8-93a1f12483b3",
            "5ffaec5a-7eb5-42f9-a0f8-93a1f12483b3",
            "AAAA",
            -100,
            -101,
            123456789,
            true
        )

        admissionRecordRepositoryImpl.save(AdmissionRecord(
            admitRecordEntity.recordId,
            admitRecordEntity.patientId,
            admitRecordEntity.localDoctorId,
            admitRecordEntity.divisionId,
            admitRecordEntity.roomNumber,
            admitRecordEntity.bedNumber,
            admitRecordEntity.privInsuranceNum,
            admitRecordEntity.isDischarged
        ))
        val actualOpt = admissionRecordTableRepository.findById(admitRecordEntity.recordId)

        assertTrue(actualOpt.isPresent)
        val actual = actualOpt.get()
        assertEquals(admitRecordEntity.recordId, actual.recordId)
        assertEquals(admitRecordEntity.patientId, actual.patientId)
        assertEquals(admitRecordEntity.localDoctorId, actual.localDoctorId)
        assertEquals(admitRecordEntity.divisionId, actual.divisionId)
        assertEquals(admitRecordEntity.roomNumber, actual.roomNumber)
        assertEquals(admitRecordEntity.bedNumber, actual.bedNumber)
        assertEquals(admitRecordEntity.privInsuranceNum, actual.privInsuranceNum)
        assertEquals(admitRecordEntity.isDischarged, actual.isDischarged)
    }

    @Test
    fun testFind_success() {
        val admitRecordEntity = AdmissionRecordEntity(
            "74f9081d-9aad-49f7-aa31-98597943fcba",
            "11f6e417-3541-4b8e-9b0f-6ec11dec5af3",
            "23f71baf-a365-4ff0-9a7e-bc25095aaf53",
            "BBBB",
            -101,
            -102,
            223456789,
            true
        )

        admissionRecordTableRepository.save(admitRecordEntity)

        val actual = admissionRecordRepositoryImpl.find(admitRecordEntity.recordId)

        assertNotNull(actual)
        assertAdmissionRecordEqualToEntity(admitRecordEntity, actual!!)
    }

    @Test
    fun testFind_doesNotExist_nullResult() {
        val actual = admissionRecordRepositoryImpl.find("433e74c9-43e1-450b-96bd-12f0cf3123a2")

        assertNull(actual)
    }

    @Test
    fun testFindByPatientId_success() {
        val admitRecordEntity = AdmissionRecordEntity(
            "16abc514-66ee-4431-92e1-2a196e7c79b3",
            "7b7d6974-3584-43e7-8b55-6fe62bc0b3d3",
            "c957dc6d-83a3-4a7b-b10d-76a8952b5d2d",
            "AAAA",
            -101,
            -102,
            223456789,
            false
        )

        admissionRecordTableRepository.save(admitRecordEntity)

        val actual = admissionRecordRepositoryImpl.findByPatientId(admitRecordEntity.patientId)

        assertNotNull(actual)
        assertAdmissionRecordEqualToEntity(admitRecordEntity, actual!!)
    }

    @Test
    fun testFindByPatientId_multipleOnSamePatient_onlyNotDischargedResult() {
        val admitRecordEntityIgnored = AdmissionRecordEntity(
            "16abc514-66ee-4431-92e1-2a196e7c79b3",
            "63c13a14-bb83-454d-8a7e-eb35b0973164",
            "c957dc6d-83a3-4a7b-b10d-76a8952b5d2d",
            "AAAA",
            -101,
            -102,
            223456789,
            true
        )
        val admitRecordEntity = AdmissionRecordEntity(
            "dd9eca53-e18f-449d-ada6-29d6d322f0c8",
            admitRecordEntityIgnored.patientId,
            "b6e0f8cf-8db6-4b20-9572-ed6260c848da",
            "AAAA",
            -105,
            -106,
            223456789,
            false
        )

        admissionRecordTableRepository.save(admitRecordEntityIgnored)
        admissionRecordTableRepository.save(admitRecordEntity)

        val actual = admissionRecordRepositoryImpl.findByPatientId(admitRecordEntity.patientId)

        assertNotNull(actual)
        assertAdmissionRecordEqualToEntity(admitRecordEntity, actual!!)
    }

    @Test
    fun testFindByPatientId_onlyDischarged_nullResult() {
        val admitRecordEntity = AdmissionRecordEntity(
            "f33d27a4-6c3d-43b3-a3d6-9ee49b23c573",
            "29ef4528-83c0-49d1-8855-7d1e88dd8804",
            "ddf9813a-ad3a-4487-bd07-05694a0df05a",
            "AAAA",
            -101,
            -102,
            223456789,
            true
        )

        admissionRecordTableRepository.save(admitRecordEntity)

        val actual = admissionRecordRepositoryImpl.findByPatientId(admitRecordEntity.patientId)

        assertNull(actual)
    }

    @Test
    fun testFindAllByPatientId_success() {
        val admitRecordEntity0 = AdmissionRecordEntity(
            "ed100f3a-531a-4924-8387-43fd62643982",
            "e0658a9f-fa3c-4948-9c80-2c7bc379e6dc",
            "bd6a61cd-7a25-4b18-8bcc-f05649a7c86f",
            "AAAA",
            -101,
            -102,
            223456789,
            true
        )
        val admitRecordEntity1 = AdmissionRecordEntity(
            "091283fb-d784-4083-8f70-c2d2655c7081",
            admitRecordEntity0.patientId,
            "f19317e4-a03f-4fa5-b736-9547284a389c",
            "AAAA",
            -105,
            -106,
            223456789,
            false
        )

        admissionRecordTableRepository.save(admitRecordEntity0)
        admissionRecordTableRepository.save(admitRecordEntity1)

        val actual = admissionRecordRepositoryImpl.findAllByPatientId(admitRecordEntity0.patientId)

        assertEquals(2, actual.size)
        var entity0Found = false
        var entity1Found = false
        if(actual[0].recordId == admitRecordEntity0.recordId) {
            assertAdmissionRecordEqualToEntity(admitRecordEntity0, actual[0])
            entity0Found = true
        } else {
            assertAdmissionRecordEqualToEntity(admitRecordEntity1, actual[0])
            entity1Found = true
        }

        if(actual[1].recordId == admitRecordEntity0.recordId) {
            assertAdmissionRecordEqualToEntity(admitRecordEntity0, actual[1])
            entity0Found = true
        } else {
            assertAdmissionRecordEqualToEntity(admitRecordEntity1, actual[1])
            entity1Found = true
        }
        assertTrue(entity0Found)
        assertTrue(entity1Found)
    }

    fun assertAdmissionRecordEqualToEntity(expectedAdmitRecordEntity: AdmissionRecordEntity, actualAdmitRecord: AdmissionRecord) {
        assertEquals(expectedAdmitRecordEntity.recordId, actualAdmitRecord.recordId)
        assertEquals(expectedAdmitRecordEntity.patientId, actualAdmitRecord.patientId)
        assertEquals(expectedAdmitRecordEntity.localDoctorId, actualAdmitRecord.localDoctor)
        assertEquals(expectedAdmitRecordEntity.divisionId, actualAdmitRecord.divisionId)
        assertEquals(expectedAdmitRecordEntity.roomNumber, actualAdmitRecord.roomNum)
        assertEquals(expectedAdmitRecordEntity.bedNumber, actualAdmitRecord.bedNumber)
        assertEquals(expectedAdmitRecordEntity.privInsuranceNum, actualAdmitRecord.privInsuranceNum)
        assertEquals(expectedAdmitRecordEntity.isDischarged, actualAdmitRecord.isDischarged)
    }
}