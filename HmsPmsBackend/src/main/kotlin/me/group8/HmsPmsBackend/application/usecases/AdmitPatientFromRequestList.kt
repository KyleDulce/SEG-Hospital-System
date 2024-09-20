package me.group8.HmsPmsBackend.application.usecases

import me.group8.HmsPmsBackend.application.dtos.queries.AdmitPatientCreateDto

interface AdmitPatientFromRequestList {
    fun admitPatientFromRequestList(admitPatientCreateDto: AdmitPatientCreateDto): Boolean
    fun rejectPatientAdmission(divisionId: String, patientId: String)
}