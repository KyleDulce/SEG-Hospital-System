package me.group8.HmsPmsBackend.application.usecases

import me.group8.HmsPmsBackend.application.dtos.queries.PatientCreateDto

interface RegisterPatient {
    fun registerPatient(patientData: PatientCreateDto): Boolean
}