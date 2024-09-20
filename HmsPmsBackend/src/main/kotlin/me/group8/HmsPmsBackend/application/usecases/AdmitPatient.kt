package me.group8.HmsPmsBackend.application.usecases

import me.group8.HmsPmsBackend.application.dtos.queries.AdmitPatientCreateDto

interface AdmitPatient {
    fun admitPatient(patientAdmission: AdmitPatientCreateDto): Boolean
    fun getNurseDivisionId(chargeNurseId: String): String?
}