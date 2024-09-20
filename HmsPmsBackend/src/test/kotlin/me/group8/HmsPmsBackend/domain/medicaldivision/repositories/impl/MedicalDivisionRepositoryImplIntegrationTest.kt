package me.group8.HmsPmsBackend.domain.medicaldivision.repositories.impl

import me.group8.HmsPmsBackend.application.db.postgres.entities.*
import me.group8.HmsPmsBackend.application.db.postgres.entities.id.MedicalDivisionAdmitRequestId
import me.group8.HmsPmsBackend.application.db.postgres.entities.id.MedicalDivisionBedId
import me.group8.HmsPmsBackend.application.db.postgres.repository.AdmitRequestTableRepository
import me.group8.HmsPmsBackend.application.db.postgres.repository.MedicalDivisionAdmitRequestRepository
import me.group8.HmsPmsBackend.application.db.postgres.repository.MedicalDivisionBedRepository
import me.group8.HmsPmsBackend.application.db.postgres.repository.MedicalDivisionTableRepository
import me.group8.HmsPmsBackend.domain.medicaldivision.entities.AdmitRequest
import me.group8.HmsPmsBackend.domain.medicaldivision.entities.MedicalDivision
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled

@SpringBootTest
@Transactional
class MedicalDivisionRepositoryImplIntegrationTest {

    @Autowired
    lateinit var medicalDivisionRepositoryImpl: MedicalDivisionRepositoryImpl

    @Autowired
    lateinit var admitRequestTableRepository: AdmitRequestTableRepository
    @Autowired
    lateinit var medicalDivisionAdmitRequestRepository: MedicalDivisionAdmitRequestRepository
    @Autowired
    lateinit var medicalDivisionBedRepository: MedicalDivisionBedRepository
    @Autowired
    lateinit var medicalDivisionTableRepository: MedicalDivisionTableRepository

    @Test
    fun testFind_success() {
        val medDivisionEntity = MedicalDivisionEntity(
            "AAAA",
            "someDiv",
            "SomeNurseId",
            "someLocation",
            1,
            123,
            "complete",
                1
        )
        val admitReq = AdmitRequestEntity(
            "fd786bdf-3bf7-41e0-a30a-3b418de1a8cb",
            "SomeNurseId",
            1,
            "RationalePart"
        )

        val medDivBedEntity = MedicalDivisionBedEntity(
            MedicalDivisionBedId(
                medDivisionEntity.identifier,
                "0d330429-c28e-4c14-b51d-c8f6e70a468a"
            )
        )
        val medDivAdmitReq = MedicalDivisionAdmitRequest(
            MedicalDivisionAdmitRequestId(
                medDivisionEntity.identifier,
                admitReq.patientId
            )
        )

        admitRequestTableRepository.save(admitReq)
        medicalDivisionBedRepository.save(medDivBedEntity)
        medicalDivisionAdmitRequestRepository.save(medDivAdmitReq)
        medicalDivisionTableRepository.save(medDivisionEntity)

        val actual = medicalDivisionRepositoryImpl.find(medDivisionEntity.identifier)

        assertNotNull(actual)
        assertEquals(medDivisionEntity.identifier, actual!!.identifier)
        assertEquals(medDivisionEntity.name, actual.name)
        assertEquals(medDivisionEntity.chargeNurseId, actual.chargeNurseId)
        assertEquals(medDivisionEntity.location, actual.location)
        assertEquals(medDivisionEntity.numBeds, actual.numBeds)
        assertEquals(medDivisionEntity.teleExt, actual.teleExt)
        assertEquals(medDivisionEntity.status, actual.status)

        assertEquals(1, actual.bedIds.size)
        assertEquals(medDivBedEntity.medicalDivisionBedId.bed_identifier, actual.bedIds[0])

        assertEquals(1, actual.admitReqs.size)
        assertEquals(admitReq.patientId, actual.admitReqs[0].patientId)
        assertEquals(admitReq.requestingChargeNurseId, actual.admitReqs[0].requestingChargeNurseId)
        assertEquals(admitReq.priority, actual.admitReqs[0].priority)
        assertEquals(admitReq.rationale, actual.admitReqs[0].rationale)
    }

    @Test
    fun testSave_Success() {
        val medDivisionEntity = MedicalDivisionEntity(
            "BBBB",
            "someDiv",
            "SomeNurseId",
            "someLocation",
            1,
            124,
            "complete",
                1
        )
        //To Add
        val admitReq1 = AdmitRequestEntity(
            "7ddde4dc-aa9c-4661-b4b7-b7acc84e35d1",
            "SomeNurseId1",
            1,
            "RationalePart1"
        )
        //To keep
        val admitReq2 = AdmitRequestEntity(
            "4be17705-336c-4f85-9b8f-4d8adcd06673",
            "SomeNurseId2",
            2,
            "RationalePart2"
        )
        //To remove
        val admitReq3 = AdmitRequestEntity(
            "9b7851de-31f9-40f2-9eed-9c0e73b3c025",
            "SomeNurseId3",
            3,
            "RationalePart3"
        )

        val medDivBedEntity = MedicalDivisionBedEntity(
            MedicalDivisionBedId(
                medDivisionEntity.identifier,
                "fc9cf445-3e2b-46a5-98b8-c78625c5ce6f"
            )
        )
        // Add
        val medDivAdmitReq1 = MedicalDivisionAdmitRequest(
            MedicalDivisionAdmitRequestId(
                medDivisionEntity.identifier,
                admitReq1.patientId
            )
        )
        //Keep
        val medDivAdmitReq2 = MedicalDivisionAdmitRequest(
            MedicalDivisionAdmitRequestId(
                medDivisionEntity.identifier,
                admitReq2.patientId
            )
        )
        //Remove
        val medDivAdmitReq3 = MedicalDivisionAdmitRequest(
            MedicalDivisionAdmitRequestId(
                medDivisionEntity.identifier,
                admitReq3.patientId
            )
        )

        admitRequestTableRepository.save(admitReq2)
        admitRequestTableRepository.save(admitReq3)
        medicalDivisionAdmitRequestRepository.save(medDivAdmitReq2)
        medicalDivisionAdmitRequestRepository.save(medDivAdmitReq3)
        medicalDivisionBedRepository.save(medDivBedEntity)
        medicalDivisionTableRepository.save(medDivisionEntity)

        val medDivObj = MedicalDivision(
            medDivisionEntity.identifier,
            medDivisionEntity.name,
            medDivisionEntity.chargeNurseId,
            medDivisionEntity.location,
            medDivisionEntity.numBeds,
            medDivisionEntity.teleExt,
            medDivisionEntity.status,
            Array<String>(1) {medDivBedEntity.medicalDivisionBedId.bed_identifier},
        )

        medDivObj.admitReqs.add(
            AdmitRequest(
                admitReq1.patientId,
                admitReq1.requestingChargeNurseId,
                admitReq1.priority,
                admitReq1.rationale
            )
        )
        medDivObj.admitReqs.add(
            AdmitRequest(
                admitReq2.patientId,
                admitReq2.requestingChargeNurseId,
                admitReq2.priority,
                admitReq2.rationale
            )
        )

        // Actual
        medicalDivisionRepositoryImpl.save(medDivObj)
        val actualOpt = medicalDivisionTableRepository.findById(medDivisionEntity.identifier)

        // Assert
        assertTrue(actualOpt.isPresent)
        val actual = actualOpt.get()
        assertEquals(medDivisionEntity.identifier, actual.identifier)
        assertEquals(medDivisionEntity.name, actual.name)
        assertEquals(medDivisionEntity.chargeNurseId, actual.chargeNurseId)
        assertEquals(medDivisionEntity.location, actual.location)
        assertEquals(medDivisionEntity.numBeds, actual.numBeds)
        assertEquals(medDivisionEntity.teleExt, actual.teleExt)
        assertEquals(medDivisionEntity.status, actual.status)

        //assert admit reqs in db objs
        assertInDbAndEqual(admitReq1, medDivAdmitReq1)
        assertInDbAndEqual(admitReq2, medDivAdmitReq2)
        assertNotInDb(admitReq3, medDivAdmitReq3)
    }

    fun assertNotInDb(admitReqEntity: AdmitRequestEntity, medDivAdmitReq: MedicalDivisionAdmitRequest) {
        assertFalse(admitRequestTableRepository.existsById(admitReqEntity.patientId))
        assertFalse(medicalDivisionAdmitRequestRepository.existsById(
            MedicalDivisionAdmitRequestId(
                medDivAdmitReq.medicalDivisionAdmitRequestId.medIddentifier,
                medDivAdmitReq.medicalDivisionAdmitRequestId.pat_identifier)
        ))
    }

    fun assertInDbAndEqual(admitReqEntity: AdmitRequestEntity, medDivAdmitReq: MedicalDivisionAdmitRequest) {
        assertTrue(admitRequestTableRepository.existsById(admitReqEntity.patientId))
        assertTrue(medicalDivisionAdmitRequestRepository.existsById(
            MedicalDivisionAdmitRequestId(
                medDivAdmitReq.medicalDivisionAdmitRequestId.medIddentifier,
                medDivAdmitReq.medicalDivisionAdmitRequestId.pat_identifier)
        ))

        val actualAdmitReq = admitRequestTableRepository.findById(admitReqEntity.patientId)
        val actualMedDivAdmitReq = medicalDivisionAdmitRequestRepository.findById(
            MedicalDivisionAdmitRequestId(
                medDivAdmitReq.medicalDivisionAdmitRequestId.medIddentifier,
                medDivAdmitReq.medicalDivisionAdmitRequestId.pat_identifier)
        )

        assertTrue(actualAdmitReq.isPresent)
        assertTrue(actualMedDivAdmitReq.isPresent)

        assertEquals(admitReqEntity.patientId, actualAdmitReq.get().patientId)
        assertEquals(admitReqEntity.requestingChargeNurseId, actualAdmitReq.get().requestingChargeNurseId)
        assertEquals(admitReqEntity.priority, actualAdmitReq.get().priority)
        assertEquals(admitReqEntity.rationale, actualAdmitReq.get().rationale)

        assertEquals(medDivAdmitReq.medicalDivisionAdmitRequestId.medIddentifier, actualMedDivAdmitReq.get().medicalDivisionAdmitRequestId.medIddentifier)
        assertEquals(medDivAdmitReq.medicalDivisionAdmitRequestId.pat_identifier, actualMedDivAdmitReq.get().medicalDivisionAdmitRequestId.pat_identifier)
    }

}