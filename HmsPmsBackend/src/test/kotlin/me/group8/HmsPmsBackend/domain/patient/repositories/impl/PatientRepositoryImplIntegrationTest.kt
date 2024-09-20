package me.group8.HmsPmsBackend.domain.patient.repositories.impl

import me.group8.HmsPmsBackend.application.db.postgres.entities.AddressEntity
import me.group8.HmsPmsBackend.application.db.postgres.entities.NextOfKinEntity
import me.group8.HmsPmsBackend.application.db.postgres.entities.PatientEntity
import me.group8.HmsPmsBackend.application.db.postgres.repository.AddressTableRepository
import me.group8.HmsPmsBackend.application.db.postgres.repository.NextOfKinTableRepository
import me.group8.HmsPmsBackend.application.db.postgres.repository.PatientTableRepository
import me.group8.HmsPmsBackend.domain.patient.entities.Address
import me.group8.HmsPmsBackend.domain.patient.entities.NextOfKin
import me.group8.HmsPmsBackend.domain.patient.entities.Patient
import me.group8.HmsPmsBackend.utils.DbTestUtils
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@SpringBootTest
@Transactional
class PatientRepositoryImplIntegrationTest {

    @Autowired
    lateinit var patientRepositoryImpl: PatientRepositoryImpl

    @Autowired
    lateinit var patientTableRepository: PatientTableRepository
    @Autowired
    lateinit var addressTableRepository: AddressTableRepository
    @Autowired
    lateinit var nextOfKinTableRepository: NextOfKinTableRepository

    @Test
    fun testSave_doesNotPreexist_success() {
        val addressEntity = AddressEntity(
            "f26d875a-2c4b-44a1-9b5f-1df47fc22904",
            1,
            "streetname",
            2,
            "abcdef",
            "some city",
            "some province",
            "some country"
        )
        val nokAddressEntity = AddressEntity(
            "8d90b3de-4ecb-4c6e-be15-f01ef650c91b",
            3,
            "streetname2",
            4,
            "abcde2",
            "some city2",
            "some province2",
            "some country2"
        )
        val patientEntity = PatientEntity(
            "a977549b-55c0-4ac2-bcc6-7246bca0f984",
            123456789,
            "fname",
            "lname",
            addressEntity.id,
            1234567890,
            DbTestUtils.generateDateSql(),
            "gender",
            "status",
            "some doct id"
        )
        val nextOfKinEntity = NextOfKinEntity(
            patientEntity.patientId,
            "fname2",
            "lname2",
            "relation",
            nokAddressEntity.id,
            1231231230
        )

        val patientObj = Patient(
            patientEntity.patientId,
            patientEntity.govInsuranceNum,
            patientEntity.firstName,
            patientEntity.lastName,
            Address(
                addressEntity.streetNum,
                addressEntity.streetName,
                addressEntity.aptNumber,
                addressEntity.postalCode,
                addressEntity.city,
                addressEntity.province,
                addressEntity.country
            ),
            patientEntity.telephone,
            patientEntity.dateOfBirth,
            patientEntity.gender,
            patientEntity.martialStatus,
            patientEntity.extDoctorId,
            NextOfKin(
                nextOfKinEntity.firstName,
                nextOfKinEntity.lastName,
                nextOfKinEntity.relationship,
                Address(
                    nokAddressEntity.streetNum,
                    nokAddressEntity.streetName,
                    nokAddressEntity.aptNumber,
                    nokAddressEntity.postalCode,
                    nokAddressEntity.city,
                    nokAddressEntity.province,
                    nokAddressEntity.country
                ),
                nextOfKinEntity.telephone
            )
        )

        // actual
        patientRepositoryImpl.save(patientObj)

        //assert
        val actualPatientOpt = patientTableRepository.findById(patientEntity.patientId)
        val actualNextOfKinOpt = nextOfKinTableRepository.findById(nextOfKinEntity.patientId)

        assertTrue(actualPatientOpt.isPresent)
        assertTrue(actualNextOfKinOpt.isPresent)

        val addressOpt = addressTableRepository.findById(actualPatientOpt.get().addressId)
        val nokAddressOpt = addressTableRepository.findById(actualNextOfKinOpt.get().addressId)

        patientEntity.addressId = actualPatientOpt.get().addressId
        nextOfKinEntity.addressId = actualNextOfKinOpt.get().addressId
        addressEntity.id = actualPatientOpt.get().addressId
        nokAddressEntity.id = actualNextOfKinOpt.get().addressId
        assertTrue(addressOpt.isPresent)
        assertTrue(nokAddressOpt.isPresent)

        val actualPatient = actualPatientOpt.get()
        val actualNextOfKin = actualNextOfKinOpt.get()
        val actualAddress = addressOpt.get()
        val actualNokAddress = nokAddressOpt.get()

        assertPatientEntityEqual(patientEntity, actualPatient)
        assertNextOfKinEqual(nextOfKinEntity, actualNextOfKin)
        assertAddressEntityEqual(addressEntity, actualAddress)
        assertAddressEntityEqual(nokAddressEntity, actualNokAddress)
    }

    @Test
    fun testSave_doesPreexist_success() {
        val oldAddressEntity = AddressEntity(
            "fbe10345-48e8-4349-be2e-f7981aa7c795",
            1,
            "streetname",
            2,
            "abcdef",
            "some city",
            "some province",
            "some country"
        )
        val oldNokAddressEntity = AddressEntity(
            "95d5000d-a52a-4661-9439-daec75a5fd70",
            3,
            "streetname2",
            4,
            "abcde2",
            "some city2",
            "some province2",
            "some country2"
        )
        val oldPatientEntity = PatientEntity(
            "057d14ce-b6e8-4257-88b6-96d70d4039a1",
            123456789,
            "fname",
            "lname",
            oldAddressEntity.id,
            1234567890,
            DbTestUtils.generateDateSql(),
            "gender",
            "status",
            "some doct id"
        )
        val oldNextOfKinEntity = NextOfKinEntity(
            oldPatientEntity.patientId,
            "fname2",
            "lname2",
            "relation",
            oldNokAddressEntity.id,
            1231231230
        )
        patientTableRepository.save(oldPatientEntity)
        nextOfKinTableRepository.save(oldNextOfKinEntity)
        addressTableRepository.save(oldAddressEntity)
        addressTableRepository.save(oldNokAddressEntity)

        val addressEntity = AddressEntity(
            oldAddressEntity.id,
            10,
            "streetname00",
            20,
            "abcd00",
            "some city00",
            "some province00",
            "some country00"
        )
        val nokAddressEntity = AddressEntity(
            oldNokAddressEntity.id,
            30,
            "streetname20",
            40,
            "abcd02",
            "some city20",
            "some province20",
            "some country20"
        )
        val patientEntity = PatientEntity(
            oldPatientEntity.patientId,
            123456700,
            "fname0",
            "lname0",
            addressEntity.id,
            1234567000,
            DbTestUtils.generateDateSql(),
            "gender0",
            "status0",
            "some doct id0"
        )
        val nextOfKinEntity = NextOfKinEntity(
            oldNextOfKinEntity.patientId,
            "fname20",
            "lname20",
            "relation0",
            nokAddressEntity.id,
            1231230000
        )

        val patientObj = Patient(
            patientEntity.patientId,
            patientEntity.govInsuranceNum,
            patientEntity.firstName,
            patientEntity.lastName,
            Address(
                addressEntity.streetNum,
                addressEntity.streetName,
                addressEntity.aptNumber,
                addressEntity.postalCode,
                addressEntity.city,
                addressEntity.province,
                addressEntity.country
            ),
            patientEntity.telephone,
            patientEntity.dateOfBirth,
            patientEntity.gender,
            patientEntity.martialStatus,
            patientEntity.extDoctorId,
            NextOfKin(
                nextOfKinEntity.firstName,
                nextOfKinEntity.lastName,
                nextOfKinEntity.relationship,
                Address(
                    nokAddressEntity.streetNum,
                    nokAddressEntity.streetName,
                    nokAddressEntity.aptNumber,
                    nokAddressEntity.postalCode,
                    nokAddressEntity.city,
                    nokAddressEntity.province,
                    nokAddressEntity.country
                ),
                nextOfKinEntity.telephone
            )
        )

        // actual
        patientRepositoryImpl.save(patientObj)

        //assert
        val actualPatientOpt = patientTableRepository.findById(patientEntity.patientId)
        val actualNextOfKinOpt = nextOfKinTableRepository.findById(nextOfKinEntity.patientId)
        val addressOpt = addressTableRepository.findById(addressEntity.id)
        val nokAddressOpt = addressTableRepository.findById(nokAddressEntity.id)

        assertTrue(actualPatientOpt.isPresent)
        assertTrue(actualNextOfKinOpt.isPresent)
        assertTrue(addressOpt.isPresent)
        assertTrue(nokAddressOpt.isPresent)

        val actualPatient = actualPatientOpt.get()
        val actualNextOfKin = actualNextOfKinOpt.get()
        val actualAddress = addressOpt.get()
        val actualNokAddress = nokAddressOpt.get()

        assertPatientEntityEqual(patientEntity, actualPatient)
        assertNextOfKinEqual(nextOfKinEntity, actualNextOfKin)
        assertAddressEntityEqual(addressEntity, actualAddress)
        assertAddressEntityEqual(nokAddressEntity, actualNokAddress)
    }

    @Test
    fun testFind_success() {
        val addressEntity = AddressEntity(
            "f26d875a-2c4b-44a1-9b5f-1df47fc22904",
            1,
            "streetname",
            2,
            "abcdef",
            "some city",
            "some province",
            "some country"
        )
        val nokAddressEntity = AddressEntity(
            "8d90b3de-4ecb-4c6e-be15-f01ef650c91b",
            3,
            "streetname2",
            4,
            "abcde2",
            "some city2",
            "some province2",
            "some country2"
        )
        val patientEntity = PatientEntity(
            "a977549b-55c0-4ac2-bcc6-7246bca0f984",
            123456789,
            "fname",
            "lname",
            addressEntity.id,
            1234567890,
            DbTestUtils.generateDateSql(),
            "gender",
            "status",
            "some doct id"
        )
        val nextOfKinEntity = NextOfKinEntity(
            patientEntity.patientId,
            "fname2",
            "lname2",
            "relation",
            nokAddressEntity.id,
            1231231230
        )
        patientTableRepository.save(patientEntity)
        nextOfKinTableRepository.save(nextOfKinEntity)
        addressTableRepository.save(addressEntity)
        addressTableRepository.save(nokAddressEntity)

        //actual
        val actual = patientRepositoryImpl.find(patientEntity.patientId)

        //assert
        assertNotNull(actual)
        assertPatientToEntity(patientEntity, actual!!)
        assertNextOfKinToEntity(nextOfKinEntity, actual.nextOfKin)
        assertAddressToEntity(addressEntity, actual.address)
        assertAddressToEntity(nokAddressEntity, actual.nextOfKin.address)
    }

    @Test
    fun testFind_doesNotExist_nullResult() {
        val actual = patientRepositoryImpl.find("eaad6896-8e05-4b8f-b10e-88200b74e226")
        assertNull(actual)
    }

    fun assertPatientToEntity(expected: PatientEntity, actual: Patient) {
        assertEquals(expected.patientId, actual.patientId)
        assertEquals(expected.govInsuranceNum, actual.govInsuranceNum)
        assertEquals(expected.firstName, actual.firstName)
        assertEquals(expected.lastName, actual.lastName)
        assertEquals(expected.telephone, actual.telephone)
        assertEquals(expected.dateOfBirth, actual.dateOfBirth)
        assertEquals(expected.gender, actual.gender)
        assertEquals(expected.martialStatus, actual.martialStatus)
        assertEquals(expected.extDoctorId, actual.extDoctorId)
    }

    fun assertAddressToEntity(expected: AddressEntity, actual: Address) {
        assertEquals(expected.streetNum, actual.streetNum)
        assertEquals(expected.streetName, actual.streetName)
        assertEquals(expected.aptNumber, actual.aptNumber)
        assertEquals(expected.postalCode, actual.postalCode)
        assertEquals(expected.city, actual.city)
        assertEquals(expected.province, actual.province)
        assertEquals(expected.country, actual.country)
    }

    fun assertNextOfKinToEntity(expected: NextOfKinEntity, actual: NextOfKin) {
        assertEquals(expected.firstName, actual.firstName)
        assertEquals(expected.lastName, actual.lastName)
        assertEquals(expected.relationship, actual.relationshipToPatient)
        assertEquals(expected.telephone, actual.telephone)
    }

    fun assertPatientEntityEqual(expectedEntity: PatientEntity, actualEntity: PatientEntity) {
        assertEquals(expectedEntity.patientId, actualEntity.patientId)
        assertEquals(expectedEntity.govInsuranceNum, actualEntity.govInsuranceNum)
        assertEquals(expectedEntity.firstName, actualEntity.firstName)
        assertEquals(expectedEntity.lastName, actualEntity.lastName)
        assertEquals(expectedEntity.addressId, actualEntity.addressId)
        assertEquals(expectedEntity.telephone, actualEntity.telephone)
        assertEquals(expectedEntity.dateOfBirth, actualEntity.dateOfBirth)
        assertEquals(expectedEntity.gender, actualEntity.gender)
        assertEquals(expectedEntity.martialStatus, actualEntity.martialStatus)
        assertEquals(expectedEntity.extDoctorId, actualEntity.extDoctorId)
    }

    fun assertAddressEntityEqual(expected: AddressEntity, actual: AddressEntity) {
        assertEquals(expected.id, actual.id)
        assertEquals(expected.streetNum, actual.streetNum)
        assertEquals(expected.streetName, actual.streetName)
        assertEquals(expected.aptNumber, actual.aptNumber)
        assertEquals(expected.postalCode, actual.postalCode)
        assertEquals(expected.city, actual.city)
        assertEquals(expected.province, actual.province)
        assertEquals(expected.country, actual.country)
    }

    fun assertNextOfKinEqual(expected: NextOfKinEntity, actual: NextOfKinEntity) {
        assertEquals(expected.patientId, actual.patientId)
        assertEquals(expected.firstName, actual.firstName)
        assertEquals(expected.lastName, actual.lastName)
        assertEquals(expected.relationship, actual.relationship)
        assertEquals(expected.addressId, actual.addressId)
        assertEquals(expected.telephone, actual.telephone)
    }
}