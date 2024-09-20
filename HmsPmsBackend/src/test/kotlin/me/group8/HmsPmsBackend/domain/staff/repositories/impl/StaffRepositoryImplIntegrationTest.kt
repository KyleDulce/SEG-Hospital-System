package me.group8.HmsPmsBackend.domain.staff.repositories.impl

import me.group8.HmsPmsBackend.application.db.postgres.entities.AddressEntity
import me.group8.HmsPmsBackend.application.db.postgres.entities.ChargeNurseEntity
import me.group8.HmsPmsBackend.application.db.postgres.entities.DoctorEntity
import me.group8.HmsPmsBackend.application.db.postgres.entities.StaffEntity
import me.group8.HmsPmsBackend.application.db.postgres.repository.AddressTableRepository
import me.group8.HmsPmsBackend.application.db.postgres.repository.ChargeNurseTableRepository
import me.group8.HmsPmsBackend.application.db.postgres.repository.DoctorTableRepository
import me.group8.HmsPmsBackend.application.db.postgres.repository.StaffTableRepository
import me.group8.HmsPmsBackend.domain.patient.entities.Address
import me.group8.HmsPmsBackend.domain.staff.entities.ChargeNurse
import me.group8.HmsPmsBackend.domain.staff.entities.Doctor
import me.group8.HmsPmsBackend.domain.staff.entities.GenericStaff
import me.group8.HmsPmsBackend.utils.DbTestUtils
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@SpringBootTest
@Transactional
class StaffRepositoryImplIntegrationTest {

    @Autowired
    lateinit var staffRepositoryImpl: StaffRepositoryImpl

    @Autowired
    lateinit var staffTableRepository: StaffTableRepository
    @Autowired
    lateinit var chargeNurseTableRepository: ChargeNurseTableRepository
    @Autowired
    lateinit var doctorTableRepository: DoctorTableRepository
    @Autowired
    lateinit var addressTableRepository: AddressTableRepository

    @Test
    fun testDoesEmployeeExist_exists_success() {
        val staffEntity = StaffEntity(
            "empId01",
            "fname",
            "lname",
            "a2e64faf-9905-4348-9220-8385d8363ab1",
            1234567890,
            DbTestUtils.generateDateSql(),
            "user",
            "pass",
            123,
            true
        )
        staffTableRepository.save(staffEntity)

        val actual = staffRepositoryImpl.doesEmployeeExist(staffEntity.employeeId)

        assertTrue(actual)
    }

    @Test
    fun testDoesEmployeeExist_doesNotExist_success() {
        val actual = staffRepositoryImpl.doesEmployeeExist("Bad id")

        assertFalse(actual)
    }

    @Test
    fun testFindStaff_success() {
        val addressEntity = AddressEntity(
            "9bdb787f-173f-4678-8539-7c3b3328805b",
            1,
            "streetname",
            2,
            "abcdef",
            "some city",
            "some province",
            "some country"
        )
        val staffEntity = StaffEntity(
            "empId02",
            "fname",
            "lname",
            addressEntity.id,
            1234567890,
            DbTestUtils.generateDateSql(),
            "user",
            "pass",
            123,
            true
        )

        staffTableRepository.save(staffEntity)
        addressTableRepository.save(addressEntity)

        val actual = staffRepositoryImpl.findstaff(staffEntity.employeeId)

        assertNotNull(actual)
        assertStaffEntityEqualsStaff(staffEntity, actual!!)
        assertAddressEntityEqualsAddress(addressEntity, actual.address)
    }

    @Test
    fun testStaff_doesNotExist_nullReturn() {
        val actual = staffRepositoryImpl.findstaff("bad emp id")

        assertNull(actual)
    }

    @Test
    fun testFindDoctor_success() {
        val addressEntity = AddressEntity(
            "1e0ef7a4-6cae-4620-ba2d-51475a9ecc58",
            1,
            "streetname",
            2,
            "abcdef",
            "some city",
            "some province",
            "some country"
        )
        val staffEntity = StaffEntity(
            "empId02",
            "fname",
            "lname",
            addressEntity.id,
            1234567890,
            DbTestUtils.generateDateSql(),
            "user",
            "pass",
            123,
            true
        )
        val doctorEntity = DoctorEntity(
            staffEntity.employeeId,
            "someDivision"
        )

        staffTableRepository.save(staffEntity)
        doctorTableRepository.save(doctorEntity)
        addressTableRepository.save(addressEntity)

        val actual = staffRepositoryImpl.findDoctor(staffEntity.employeeId)

        assertNotNull(actual)
        assertStaffEntityEqualsStaff(staffEntity, actual!!)
        assertAddressEntityEqualsAddress(addressEntity, actual.address)
        assertEquals(doctorEntity.divisionName, actual.divisionName)
    }

    @Test
    fun testFindNurse_success() {
        val addressEntity = AddressEntity(
            "106015f5-81c4-4bd4-8860-d07ca7221dbf",
            1,
            "streetname",
            2,
            "abcdef",
            "some city",
            "some province",
            "some country"
        )
        val staffEntity = StaffEntity(
            "empId02",
            "fname",
            "lname",
            addressEntity.id,
            1234567890,
            DbTestUtils.generateDateSql(),
            "user",
            "pass",
            123,
            true
        )
        val nurseEntity = ChargeNurseEntity(
            staffEntity.employeeId,
            111
        )

        staffTableRepository.save(staffEntity)
        chargeNurseTableRepository.save(nurseEntity)
        addressTableRepository.save(addressEntity)

        val actual = staffRepositoryImpl.findNurse(staffEntity.employeeId)

        assertNotNull(actual)
        assertStaffEntityEqualsStaff(staffEntity, actual!!)
        assertAddressEntityEqualsAddress(addressEntity, actual.address)
        assertEquals(nurseEntity.bipperExtension, actual.bipperExtension)
    }

    @Test
    fun testSaveStaff_success() {
        val addressEntity = AddressEntity(
            "bc7e0619-e356-473c-a73c-db39012cccdb",
            1,
            "streetname",
            2,
            "abcdef",
            "some city",
            "some province",
            "some country"
        )
        val staffEntity = StaffEntity(
            "empId10",
            "fname",
            "lname",
            addressEntity.id,
            1234567890,
            DbTestUtils.generateDateSql(),
            "user",
            "pass",
            123,
            true
        )

        val staff = createStaffFromEntities(staffEntity, addressEntity)

        staffRepositoryImpl.saveStaff(staff)

        val staffEntOpt = staffTableRepository.findById(staffEntity.employeeId)
        assertTrue(staffEntOpt.isPresent)
        addressEntity.id = staffEntOpt.get().addressId
        val addressOpt = addressTableRepository.findById(staffEntOpt.get().addressId)
        assertTrue(addressOpt.isPresent)

        val actualStaff = staffEntOpt.get()
        val actualAddress = addressOpt.get()

        assertStaffEqual(staffEntity, actualStaff)
        assertAddressEqual(addressEntity, actualAddress)
    }

    @Test
    fun testSaveStaff_exists_success() {
        val oldAddressEntity = AddressEntity(
            "b142b16b-4520-47d5-b2c1-1724aa65a057",
            1,
            "streetname-old",
            5,
            "abcold",
            "some city-old",
            "some province-old",
            "some country-old"
        )
        val oldStaffEntity = StaffEntity(
            "empId11",
            "fname-old",
            "lname-old",
            oldAddressEntity.id,
            1134567890,
            DbTestUtils.generateDateSql(),
            "user-old",
            "pass-old",
            113,
            true
        )
        addressTableRepository.save(oldAddressEntity)
        staffTableRepository.save(oldStaffEntity)

        val addressEntity = AddressEntity(
            oldAddressEntity.id,
            1,
            "streetname",
            2,
            "ahbcef",
            "some city",
            "some province",
            "some country"
        )
        val staffEntity = StaffEntity(
            oldStaffEntity.employeeId,
            "fname",
            "lname",
            addressEntity.id,
            1234567890,
            DbTestUtils.generateDateSql(),
            "user",
            "pass",
            123,
            true
        )

        val staff = createStaffFromEntities(staffEntity, addressEntity)

        staffRepositoryImpl.saveStaff(staff)

        val staffEntOpt = staffTableRepository.findById(staffEntity.employeeId)
        val addressOpt = addressTableRepository.findById(staffEntOpt.get().addressId)
        assertTrue(staffEntOpt.isPresent)
        assertTrue(addressOpt.isPresent)

        val actualStaff = staffEntOpt.get()
        val actualAddress = addressOpt.get()

        assertStaffEqual(staffEntity, actualStaff)
        assertAddressEqual(addressEntity, actualAddress)
    }

    @Test
    fun testSaveDoctor_success() {
        val addressEntity = AddressEntity(
            "2e1ecaba-4ca1-4a5f-8e3e-ec639849025a",
            1,
            "streetname",
            2,
            "abcdef",
            "some city",
            "some province",
            "some country"
        )
        val staffEntity = StaffEntity(
            "empId12",
            "fname",
            "lname",
            addressEntity.id,
            1234567890,
            DbTestUtils.generateDateSql(),
            "user",
            "pass",
            123,
            true
        )
        val doctorEntity = DoctorEntity(
            staffEntity.employeeId,
            "some division"
        )

        val staff = createDoctorFromEntities(staffEntity, addressEntity, doctorEntity)

        staffRepositoryImpl.saveDoctor(staff)

        val staffEntOpt = staffTableRepository.findById(staffEntity.employeeId)
        assertTrue(staffEntOpt.isPresent)
        addressEntity.id = staffEntOpt.get().addressId
        val addressOpt = addressTableRepository.findById(staffEntOpt.get().addressId)
        assertTrue(addressOpt.isPresent)
        val doctorEntOpt = doctorTableRepository.findById(staffEntity.employeeId)
        assertTrue(doctorEntOpt.isPresent)

        val actualStaff = staffEntOpt.get()
        val actualAddress = addressOpt.get()
        val actualDoctor = doctorEntOpt.get()

        assertStaffEqual(staffEntity, actualStaff)
        assertAddressEqual(addressEntity, actualAddress)
        assertEquals(doctorEntity.divisionName, actualDoctor.divisionName)
    }

    @Test
    fun testSaveNurse_success() {
        val addressEntity = AddressEntity(
            "32bf2dd9-ee15-4b40-a522-53914d28ae21",
            1,
            "streetname",
            2,
            "abcdef",
            "some city",
            "some province",
            "some country"
        )
        val staffEntity = StaffEntity(
            "empId13",
            "fname",
            "lname",
            addressEntity.id,
            1234567890,
            DbTestUtils.generateDateSql(),
            "user",
            "pass",
            123,
            true
        )
        val nurseEntity = ChargeNurseEntity(
            staffEntity.employeeId,
            333
        )

        val staff = createNurseFromEntities(staffEntity, addressEntity, nurseEntity)

        staffRepositoryImpl.saveNurse(staff)

        val staffEntOpt = staffTableRepository.findById(staffEntity.employeeId)
        assertTrue(staffEntOpt.isPresent)
        addressEntity.id = staffEntOpt.get().addressId
        val addressOpt = addressTableRepository.findById(staffEntOpt.get().addressId)
        assertTrue(addressOpt.isPresent)
        val nurseEntOpt = chargeNurseTableRepository.findById(staffEntity.employeeId)
        assertTrue(nurseEntOpt.isPresent)

        val actualStaff = staffEntOpt.get()
        val actualAddress = addressOpt.get()
        val actualNurse = nurseEntOpt.get()

        assertStaffEqual(staffEntity, actualStaff)
        assertAddressEqual(addressEntity, actualAddress)
        assertEquals(actualNurse.bipperExtension, actualNurse.bipperExtension)
    }

    fun assertStaffEntityEqualsStaff(expected: StaffEntity, actual: GenericStaff) {
        assertEquals(expected.employeeId, actual.employeeId)
        assertEquals(expected.firstName, actual.firstName)
        assertEquals(expected.lastName, actual.lastName)
        assertEquals(expected.telephone, actual.telephone)
        assertEquals(expected.dateOfBirth, actual.dateOfBirth)
        assertEquals(expected.userName, actual.userName)
        assertEquals(expected.password, actual.password)
        assertEquals(expected.teleExtension, actual.teleExtension)
        assertEquals(expected.modifyPermission, actual.modifyPermission)
    }

    fun assertStaffEqual(expected: StaffEntity, actual: StaffEntity) {
        assertEquals(expected.employeeId, actual.employeeId)
        assertEquals(expected.firstName, actual.firstName)
        assertEquals(expected.lastName, actual.lastName)
        assertEquals(expected.telephone, actual.telephone)
        assertEquals(expected.dateOfBirth, actual.dateOfBirth)
        assertEquals(expected.userName, actual.userName)
        assertEquals(expected.password, actual.password)
        assertEquals(expected.teleExtension, actual.teleExtension)
        assertEquals(expected.modifyPermission, actual.modifyPermission)
    }

    fun assertAddressEntityEqualsAddress(expected: AddressEntity, actual: Address) {
        assertEquals(expected.streetNum, actual.streetNum)
        assertEquals(expected.streetName, actual.streetName)
        assertEquals(expected.aptNumber, actual.aptNumber)
        assertEquals(expected.postalCode, actual.postalCode)
        assertEquals(expected.city, actual.city)
        assertEquals(expected.province, actual.province)
        assertEquals(expected.country, actual.country)
    }

    fun assertAddressEqual(expected: AddressEntity, actual: AddressEntity) {
        assertEquals(expected.streetNum, actual.streetNum)
        assertEquals(expected.streetName, actual.streetName)
        assertEquals(expected.aptNumber, actual.aptNumber)
        assertEquals(expected.postalCode, actual.postalCode)
        assertEquals(expected.city, actual.city)
        assertEquals(expected.province, actual.province)
        assertEquals(expected.country, actual.country)
    }

    fun createAddressFromEntity(addressEntity: AddressEntity): Address {
        return Address(
            addressEntity.streetNum,
            addressEntity.streetName,
            addressEntity.aptNumber,
            addressEntity.postalCode,
            addressEntity.city,
            addressEntity.province,
            addressEntity.country
        )
    }

    fun createStaffFromEntities(staffEntity: StaffEntity, addressEntity: AddressEntity): GenericStaff {
        return GenericStaff(
            staffEntity.firstName,
            staffEntity.lastName,
            createAddressFromEntity(addressEntity),
            staffEntity.telephone,
            staffEntity.employeeId,
            staffEntity.userName,
            staffEntity.password,
            staffEntity.teleExtension,
            staffEntity.dateOfBirth,
            staffEntity.modifyPermission
        )
    }

    fun createDoctorFromEntities(staffEntity: StaffEntity, addressEntity: AddressEntity, doctorEntity: DoctorEntity): Doctor {
        return Doctor(
            staffEntity.firstName,
            staffEntity.lastName,
            createAddressFromEntity(addressEntity),
            staffEntity.telephone,
            staffEntity.employeeId,
            staffEntity.userName,
            staffEntity.password,
            staffEntity.teleExtension,
            staffEntity.dateOfBirth,
            staffEntity.modifyPermission,
            doctorEntity.divisionName
        )
    }

    fun createNurseFromEntities(staffEntity: StaffEntity, addressEntity: AddressEntity, nurseEntity: ChargeNurseEntity): ChargeNurse {
        return ChargeNurse(
            staffEntity.firstName,
            staffEntity.lastName,
            createAddressFromEntity(addressEntity),
            staffEntity.telephone,
            staffEntity.employeeId,
            staffEntity.userName,
            staffEntity.password,
            staffEntity.teleExtension,
            staffEntity.dateOfBirth,
            staffEntity.modifyPermission,
            nurseEntity.bipperExtension
        )
    }
}