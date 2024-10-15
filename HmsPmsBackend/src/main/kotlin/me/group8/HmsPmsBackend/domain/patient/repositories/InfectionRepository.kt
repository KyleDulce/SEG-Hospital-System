package me.group8.HmsPmsBackend.domain.patient.repositories

import me.group8.HmsPmsBackend.domain.patient.entities.Infection

interface InfectionRepository {
    fun save(infection: Infection): Infection
    fun find(infectionId: String): Infection?
    fun findAllByPatientId(patientId: String): Array<Infection>
}