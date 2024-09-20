package me.group8.HmsPmsBackend.contracts.steps

import me.group8.HmsPmsBackend.application.dtos.queries.AddressCreateDto
import me.group8.HmsPmsBackend.application.dtos.queries.NextOfKinCreateDto
import me.group8.HmsPmsBackend.application.dtos.queries.PatientCreateDto
import me.group8.HmsPmsBackend.application.dtos.queries.StaffMemberCreateDto
import me.group8.HmsPmsBackend.domain.medicaldivision.entities.AdmitRequest
import me.group8.HmsPmsBackend.domain.medicaldivision.entities.Bed
import me.group8.HmsPmsBackend.domain.medicaldivision.entities.MedicalDivision
import me.group8.HmsPmsBackend.domain.patient.entities.Address
import me.group8.HmsPmsBackend.domain.patient.entities.AdmissionRecord
import me.group8.HmsPmsBackend.domain.patient.entities.NextOfKin
import me.group8.HmsPmsBackend.domain.patient.entities.Patient
import me.group8.HmsPmsBackend.domain.staff.entities.ChargeNurse
import me.group8.HmsPmsBackend.domain.staff.entities.Doctor
import me.group8.HmsPmsBackend.domain.staff.entities.GenericStaff
import me.group8.HmsPmsBackend.teststubs.repositories.BedRepositoryStub
import org.junit.jupiter.api.Assertions
import java.util.Date

class StepUtils {
    companion object {
        fun createAddress(): Address {
            return Address(
                1,
                "Street",
                2,
                "Postal",
                "city",
            "province",
            "country"
            )
        }

        fun createAddressDto(): AddressCreateDto {
            return AddressCreateDto(
                1,
                "Street",
                2,
                "Postal",
                "city",
                "province",
                "country"
            )
        }

        fun createDate(): Date {
            return Date(100000000)
        }

        fun createChargeNurse(modify: Boolean = true): ChargeNurse {
            return ChargeNurse(
                "Name1",
                "Name2",
                createAddress(),
                1,
                "SomeId",
                "User",
                "Password",
                2,
                createDate(),
                modify,
                1
            )
        }

        fun createDoctor(modify: Boolean = true): Doctor {
            return Doctor(
                "Name1",
                "Name2",
                createAddress(),
                1,
                "SomeId",
                "User",
                "Password",
                2,
                createDate(),
                modify,
                "division"
            )
        }

        fun createGenericStaff(modify: Boolean = true): GenericStaff {
            return GenericStaff(
                "Name1",
                "Name2",
                createAddress(),
                1,
                "SomeId",
                "User",
                "Password",
                2,
                createDate(),
                modify
            )
        }

        fun createBed(available: Boolean = true): Bed {
            return Bed(
                "id1",
                1,
                2,
                available
            )
        }

        fun createDivision(isComplete: Boolean = false, bed: Bed? = null): MedicalDivision {
            return MedicalDivision(
                "medDiv",
                "name",
                "some",
                "loc",
                1,
                2,
                when(isComplete) {
                    true -> "complete"
                    false -> "incomplete"
                },
                Array(1) {
                    when (bed != null) {
                        true -> bed
                        false -> createBed()
                    }.identifier
                }
            )
        }

        fun createAdmitRequest(patientId: String = "id1", requestingNurse: String = "id2"): AdmitRequest {
            return AdmitRequest(
                patientId,
                requestingNurse,
                1,
                "reason"
            )
        }

        fun createNextOfKin(): NextOfKin {
            return NextOfKin(
                "Kinname",
                "Kinname",
                "relation",
                createAddress(),
                1
            )
        }

        fun createNextOfKinDto(): NextOfKinCreateDto {
            return NextOfKinCreateDto(
                "Kinname",
                "Kinname",
                createAddressDto(),
                1,
                "relation"
            )
        }

        fun createPatient(): Patient {
            return Patient(
                "somePid",
                3,
                "name1",
                "name2",
                createAddress(),
                4,
                createDate(),
                "gen",
                "single",
                "someOtherId",
                createNextOfKin()
            )
        }

        fun createPatientDto(): PatientCreateDto {
            return PatientCreateDto(
                "name1",
                "name2",
                createAddressDto(),
                4,
                createDate(),
                "gender",
                "status",
                "some doctor",
                createNextOfKinDto(),
                1
            )
        }

        fun createAdmission(discharged: Boolean = false, patientId: String = "id1", localDoctor: String = "some"): AdmissionRecord {
            return AdmissionRecord(
                "admit1",
                patientId,
                localDoctor,
                "medDiv",
                1,
                2,
                3,
                discharged
            )
        }

        fun createStaffMemberCreateDto(id: String = "some random id"): StaffMemberCreateDto {
            return StaffMemberCreateDto(
                "Name1",
                "Name2",
                createAddressDto(),
                2,
                3,
                createDate(),
                id,
                "user",
                "password",
                true
            )
        }

        fun saveBedToRepo(bed: Bed, repository: BedRepositoryStub) {
            repository.bedMap[bed.identifier] = bed;
        }

        fun clonePatient(patient: Patient): Patient {
            return Patient(
                patient.patientId,
                patient.govInsuranceNum,
                patient.firstName,
                patient.lastName,
                patient.address,
                patient.telephone,
                patient.dateOfBirth,
                patient.gender,
                patient.martialStatus,
                patient.extDoctorId,
                patient.nextOfKin,
            )
        }

        fun assertPatientEqualToDto(patient: Patient, requestedPatientInfoChange: PatientCreateDto) {
            Assertions.assertEquals(requestedPatientInfoChange.firstName, patient.firstName)
            Assertions.assertEquals(requestedPatientInfoChange.lastName, patient.lastName)
            Assertions.assertEquals(requestedPatientInfoChange.telephone, patient.telephone)
            Assertions.assertEquals(requestedPatientInfoChange.dateOfBirth, patient.dateOfBirth)
            Assertions.assertEquals(requestedPatientInfoChange.gender, patient.gender)
            Assertions.assertEquals(requestedPatientInfoChange.maritalStatus, patient.martialStatus)
            Assertions.assertEquals(requestedPatientInfoChange.doctorId, patient.extDoctorId)
            Assertions.assertEquals(requestedPatientInfoChange.govInsuranceNum, patient.govInsuranceNum)
            Assertions.assertEquals(requestedPatientInfoChange.address.streetNum, patient.address.streetNum)
            Assertions.assertEquals(requestedPatientInfoChange.address.streetName, patient.address.streetName)
            Assertions.assertEquals(requestedPatientInfoChange.address.aptNumber, patient.address.aptNumber)
            Assertions.assertEquals(requestedPatientInfoChange.address.postalCode, patient.address.postalCode)
            Assertions.assertEquals(requestedPatientInfoChange.address.city, patient.address.city)
            Assertions.assertEquals(requestedPatientInfoChange.address.province, patient.address.province)
            Assertions.assertEquals(requestedPatientInfoChange.address.country, patient.address.country)
            Assertions.assertEquals(requestedPatientInfoChange.nextOfKin.firstName, patient.nextOfKin.firstName)
            Assertions.assertEquals(requestedPatientInfoChange.nextOfKin.lastName, patient.nextOfKin.lastName)
            Assertions.assertEquals(requestedPatientInfoChange.nextOfKin.telephone, patient.nextOfKin.telephone)
            Assertions.assertEquals(requestedPatientInfoChange.nextOfKin.relationship, patient.nextOfKin.relationshipToPatient
            )
        }

        fun assertPatientsEqual(patient1: Patient, patient2: Patient) {
            Assertions.assertEquals(patient2.firstName, patient1.firstName)
            Assertions.assertEquals(patient2.lastName, patient1.lastName)
            Assertions.assertEquals(patient2.telephone, patient1.telephone)
            Assertions.assertEquals(patient2.dateOfBirth, patient1.dateOfBirth)
            Assertions.assertEquals(patient2.gender, patient1.gender)
            Assertions.assertEquals(patient2.martialStatus, patient1.martialStatus)
            Assertions.assertEquals(patient2.extDoctorId, patient1.extDoctorId)
            Assertions.assertEquals(patient2.govInsuranceNum, patient1.govInsuranceNum)
            Assertions.assertEquals(patient2.address.streetNum, patient1.address.streetNum)
            Assertions.assertEquals(patient2.address.streetName, patient1.address.streetName)
            Assertions.assertEquals(patient2.address.aptNumber, patient1.address.aptNumber)
            Assertions.assertEquals(patient2.address.postalCode, patient1.address.postalCode)
            Assertions.assertEquals(patient2.address.city, patient1.address.city)
            Assertions.assertEquals(patient2.address.province, patient1.address.province)
            Assertions.assertEquals(patient2.address.country, patient1.address.country)
            Assertions.assertEquals(patient2.nextOfKin.firstName, patient1.nextOfKin.firstName)
            Assertions.assertEquals(patient2.nextOfKin.lastName, patient1.nextOfKin.lastName)
            Assertions.assertEquals(patient2.nextOfKin.telephone, patient1.nextOfKin.telephone)
            Assertions.assertEquals(patient2.nextOfKin.relationshipToPatient, patient1.nextOfKin.relationshipToPatient
            )
        }
    }
}