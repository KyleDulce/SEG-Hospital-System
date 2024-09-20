package me.group8.HmsPmsBackend.domain.patient.repositories.impl

import me.group8.HmsPmsBackend.application.db.DbUtils
import me.group8.HmsPmsBackend.application.db.postgres.entities.AddressEntity
import me.group8.HmsPmsBackend.application.db.postgres.entities.NextOfKinEntity
import me.group8.HmsPmsBackend.application.db.postgres.entities.PatientEntity
import me.group8.HmsPmsBackend.application.db.postgres.repository.AddressTableRepository
import me.group8.HmsPmsBackend.application.db.postgres.repository.NextOfKinTableRepository
import me.group8.HmsPmsBackend.application.db.postgres.repository.PatientTableRepository
import me.group8.HmsPmsBackend.domain.patient.entities.NextOfKin
import me.group8.HmsPmsBackend.domain.patient.entities.Patient
import me.group8.HmsPmsBackend.domain.patient.repositories.PatientRepository
import me.group8.HmsPmsBackend.utils.TypeUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class PatientRepositoryImpl(
    private val patientTableRepository: PatientTableRepository,
    private val addressTableRepository: AddressTableRepository,
    private val nextOfKinTableRepository: NextOfKinTableRepository
): PatientRepository {
    @Transactional(propagation = Propagation.REQUIRED)
    override fun save(patient: Patient): Patient {
        // check if patient already exists
        val existingPatient = patientTableRepository.existsById(patient.patientId)

        val patientAddress: AddressEntity
        val nokAddress: AddressEntity

        if(existingPatient) {
            val oldPatientEntity = patientTableRepository.findById(patient.patientId)
            val oldNokEntity = nextOfKinTableRepository.findById(patient.patientId)

            val oldPatientAddress = addressTableRepository.findById(oldPatientEntity.get().addressId).get()
            val oldNokAddress = addressTableRepository.findById(oldNokEntity.get().addressId).get()

            patientAddress = DbUtils.addressToEntity(patient.address, oldPatientAddress.id)
            nokAddress = DbUtils.addressToEntity(patient.nextOfKin.address, oldNokAddress.id)
        } else {
            patientAddress = DbUtils.addressToEntity(patient.address)
            nokAddress = DbUtils.addressToEntity(patient.nextOfKin.address)
        }

        val nextOfKinEntity = nextOfKinToEntity(patient.nextOfKin, patient.patientId, nokAddress.id)
        val patientEntity = patientToEntity(patient, patientAddress.id)

        patientTableRepository.save(patientEntity)
        nextOfKinTableRepository.save(nextOfKinEntity)
        addressTableRepository.save(patientAddress)
        addressTableRepository.save(nokAddress)

        return patient
    }

    override fun find(patientId: String): Patient? {
        val patientOpt = patientTableRepository.findById(patientId)
        if (patientOpt.isEmpty) {
            return null
        }

        val addressOpt = addressTableRepository.findById(patientOpt.get().addressId)
        if (addressOpt.isEmpty) {
            System.err.println("Something went wrong, patient without address!")
            return null
        }

        val nextOfKinOpt = nextOfKinTableRepository.findById(patientOpt.get().patientId)
        if (nextOfKinOpt.isEmpty) {
            System.err.println("Something went wrong, patient without next of Kin!")
            return null
        }

        val nokAddressOpt = addressTableRepository.findById(nextOfKinOpt.get().addressId)
        if (nokAddressOpt.isEmpty) {
            System.err.println("Something went wrong, next of kin without address!")
            return null
        }

        return entityToPatient(
            patientOpt.get(),
            addressOpt.get(),
            nextOfKinOpt.get(),
            nokAddressOpt.get()
        )
    }

    private fun entityToPatient(patientEntity: PatientEntity, patientAddressEntity: AddressEntity, nextOfKinEntity: NextOfKinEntity, nextofKinAddressEntity: AddressEntity): Patient {
        return Patient(
            patientEntity.patientId,
            patientEntity.govInsuranceNum,
            patientEntity.firstName,
            patientEntity.lastName,
            DbUtils.entityToAddress(patientAddressEntity),
            patientEntity.telephone,
            patientEntity.dateOfBirth,
            patientEntity.gender,
            patientEntity.martialStatus,
            patientEntity.extDoctorId,
            entityToNextOfKin(nextOfKinEntity, nextofKinAddressEntity)
        )
    }

    private fun patientToEntity(patient: Patient, addressId: String): PatientEntity {
        return PatientEntity(
            patient.patientId,
            patient.govInsuranceNum,
            patient.firstName,
            patient.lastName,
            addressId,
            patient.telephone,
            TypeUtils.utilDateToSqlDate(patient.dateOfBirth),
            patient.gender,
            patient.martialStatus,
            patient.extDoctorId
        )
    }

    private fun entityToNextOfKin(nextOfKinEntity: NextOfKinEntity, nextOfKinAddress: AddressEntity): NextOfKin {
        return NextOfKin(
            nextOfKinEntity.firstName,
            nextOfKinEntity.lastName,
            nextOfKinEntity.relationship,
            DbUtils.entityToAddress(nextOfKinAddress),
            nextOfKinEntity.telephone,
        )
    }

    private fun nextOfKinToEntity(nextOfKin: NextOfKin, patientId: String, addressId: String): NextOfKinEntity {
        return NextOfKinEntity(
            patientId,
            nextOfKin.firstName,
            nextOfKin.lastName,
            nextOfKin.relationshipToPatient,
            addressId,
            nextOfKin.telephone
        )
    }
}