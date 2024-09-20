package me.group8.HmsPmsBackend.application.usecases

import me.group8.HmsPmsBackend.application.dtos.queries.PatientCreateDto

interface UpdatePatientFile {
    fun updatePatientFile(patientId: String, employeeId: String, patientInfoUpdated: PatientCreateDto): Boolean
}