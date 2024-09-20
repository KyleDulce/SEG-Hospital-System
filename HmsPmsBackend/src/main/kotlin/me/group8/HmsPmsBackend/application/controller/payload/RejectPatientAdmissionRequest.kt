package me.group8.HmsPmsBackend.application.controller.payload

data class RejectPatientAdmissionRequest(
    val divisionId: String,
    val patientId: String
)
