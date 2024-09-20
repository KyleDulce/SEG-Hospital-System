package me.group8.HmsPmsBackend.domain.patient.repositories

import me.group8.HmsPmsBackend.domain.patient.entities.Patient

interface PatientRepository {
    fun save(patient: Patient): Patient
    fun find(patientId: String): Patient?
}