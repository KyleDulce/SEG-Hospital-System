package me.group8.HmsPmsBackend.domain.patient.factories

import me.group8.HmsPmsBackend.application.dtos.queries.PatientCreateDto
import me.group8.HmsPmsBackend.domain.patient.entities.Address
import me.group8.HmsPmsBackend.domain.patient.entities.NextOfKin
import me.group8.HmsPmsBackend.domain.patient.entities.Patient
import java.util.*
import org.springframework.stereotype.Service


@Service
class PatientFactory {
    fun getNewPatientId(): String {
        return UUID.randomUUID().toString()
    }

    fun createNewPatient(patientInfo: PatientCreateDto, newPatientId: String): Patient {
        return Patient(
            newPatientId,
            patientInfo.govInsuranceNum,
            patientInfo.firstName,
            patientInfo.lastName,
            Address(
                patientInfo.address.streetNum,
                patientInfo.address.streetName,
                patientInfo.address.aptNumber,
                patientInfo.address.postalCode,
                patientInfo.address.city,
                patientInfo.address.province,
                patientInfo.address.country,
            ),
            patientInfo.telephone,
            patientInfo.dateOfBirth,
            patientInfo.gender,
            patientInfo.maritalStatus,
            patientInfo.doctorId,
            NextOfKin(
                patientInfo.nextOfKin.firstName,
                patientInfo.nextOfKin.lastName,
                patientInfo.nextOfKin.relationship,
                Address(
                    patientInfo.nextOfKin.address.streetNum,
                    patientInfo.nextOfKin.address.streetName,
                    patientInfo.nextOfKin.address.aptNumber,
                    patientInfo.nextOfKin.address.postalCode,
                    patientInfo.nextOfKin.address.city,
                    patientInfo.nextOfKin.address.province,
                    patientInfo.nextOfKin.address.country,
                ),
                patientInfo.nextOfKin.telephone,
            )
        )
    }
}