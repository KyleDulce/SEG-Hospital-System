package me.group8.HmsPmsBackend.teststubs.repositories

import me.group8.HmsPmsBackend.domain.patient.entities.Patient
import me.group8.HmsPmsBackend.domain.patient.repositories.PatientRepository

class PatientRepositoryStub: PatientRepository {
    val patientMap: MutableMap<String, Patient> = HashMap()

    override fun save(patient: Patient): Patient {
        patientMap[patient.patientId] = patient
        return patient
    }

    override fun find(patientId: String): Patient? {
        return patientMap[patientId]
    }
}