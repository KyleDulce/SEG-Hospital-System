package me.group8.HmsPmsBackend.domain.medication.repositories

import me.group8.HmsPmsBackend.domain.medication.entities.Medication

interface MedicationRepository {
    fun save(medication: Medication): Medication
    fun findAllByPatientId(patientId: String): Array<Medication>}