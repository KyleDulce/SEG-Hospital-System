package me.group8.HmsPmsBackend.domain.medication.repositories.impl

import me.group8.HmsPmsBackend.application.db.postgres.entities.MedicationAdminEntity
import me.group8.HmsPmsBackend.application.db.postgres.entities.MedicationEntity
import me.group8.HmsPmsBackend.application.db.postgres.entities.id.MedicationAdminId
import me.group8.HmsPmsBackend.application.db.postgres.entities.id.MedicationId
import me.group8.HmsPmsBackend.application.db.postgres.repository.MedicationAdministrationTableRepository
import me.group8.HmsPmsBackend.application.db.postgres.repository.MedicationTableRepository
import me.group8.HmsPmsBackend.domain.medication.entities.Medication
import me.group8.HmsPmsBackend.domain.medication.entities.MedicationAdministration
import me.group8.HmsPmsBackend.utils.DbTestUtils
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import org.springframework.beans.factory.annotation.Autowired

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@SpringBootTest
@Transactional
class MedicationRepositoryImplIntegrationTest {

    @Autowired
    lateinit var medicationRepositoryImpl: MedicationRepositoryImpl

    @Autowired
    lateinit var medicationTableRepository: MedicationTableRepository
    @Autowired
    lateinit var medicationAdministrationTableRepository: MedicationAdministrationTableRepository

    @Test
    fun testSave_success() {
        val fakeStartDate = DbTestUtils.generateDateSql()
        val fakeEndDate = DbTestUtils.generateDateSql()
        val fakeAdminDate = DbTestUtils.generateDateSql()
        val medicationEntity = MedicationEntity(
            MedicationId(
                123456789,
                "81d7d8d6-ac40-4cc6-b846-560dd65ed9dd",
                fakeStartDate,
                fakeEndDate
            ),
            "name1",
            2,
            3,
            "administration"
        )
        val medicationAdminEntity = MedicationAdminEntity(
            MedicationAdminId(
                medicationEntity.medicationId.medication_num,
                medicationEntity.medicationId.patientId,
                fakeStartDate,
                fakeEndDate,
                fakeAdminDate
            ),
            2
        )
        val medObj = Medication(
            medicationEntity.medicationId.patientId,
            medicationEntity.medicationId.medication_num,
            medicationEntity.name,
            medicationEntity.unitByDay,
            medicationEntity.adminByDay,
            medicationEntity.methodOfAdmin,
            medicationEntity.medicationId.startDate,
            medicationEntity.medicationId.endDate
        )
        medObj.medAdmins.add(MedicationAdministration(
            medicationAdminEntity.medicationAdminId.time,
            medicationAdminEntity.numUnits
        ))

        //actual
        medicationRepositoryImpl.save(medObj)

        //assert
        assertTrue(medicationTableRepository.existsById(MedicationId(
            medicationEntity.medicationId.medication_num,
            medicationEntity.medicationId.patientId,
            fakeStartDate,
            fakeEndDate
        )))
        assertTrue(medicationAdministrationTableRepository.existsById(MedicationAdminId(
            medicationEntity.medicationId.medication_num,
            medicationEntity.medicationId.patientId,
            fakeStartDate,
            fakeEndDate,
            fakeAdminDate
        )))

        val actualMed = medicationTableRepository.findById(MedicationId(
            medicationEntity.medicationId.medication_num,
            medicationEntity.medicationId.patientId,
            fakeStartDate,
            fakeEndDate
        )).get()
        val actualAdmin = medicationAdministrationTableRepository.findById(MedicationAdminId(
            medicationEntity.medicationId.medication_num,
            medicationEntity.medicationId.patientId,
            fakeStartDate,
            fakeEndDate,
            fakeAdminDate
        )).get()

        assertEquals(medicationEntity.medicationId.medication_num, actualMed.medicationId.medication_num)
        assertEquals(medicationEntity.medicationId.patientId, actualMed.medicationId.patientId)
        assertEquals(medicationEntity.medicationId.startDate, actualMed.medicationId.startDate)
        assertEquals(medicationEntity.medicationId.endDate, actualMed.medicationId.endDate)
        assertEquals(medicationEntity.name, actualMed.name)
        assertEquals(medicationEntity.unitByDay, actualMed.unitByDay)
        assertEquals(medicationEntity.adminByDay, actualMed.adminByDay)
        assertEquals(medicationEntity.methodOfAdmin, actualMed.methodOfAdmin)

        assertEquals(medicationAdminEntity.medicationAdminId.medication_num, actualAdmin.medicationAdminId.medication_num)
        assertEquals(medicationAdminEntity.medicationAdminId.patientId, actualAdmin.medicationAdminId.patientId)
        assertEquals(medicationAdminEntity.medicationAdminId.startDate, actualAdmin.medicationAdminId.startDate)
        assertEquals(medicationAdminEntity.medicationAdminId.endDate, actualAdmin.medicationAdminId.endDate)
        assertEquals(medicationAdminEntity.numUnits, actualAdmin.numUnits)
    }
}