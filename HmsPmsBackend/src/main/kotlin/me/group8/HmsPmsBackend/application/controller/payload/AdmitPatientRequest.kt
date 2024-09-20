package me.group8.HmsPmsBackend.application.controller.payload

data class AdmitPatientRequest(
    val privateInsuranceNumber: Long,
    val patientId: String,
    val divisionId: String,
    val roomNum: Int,
    val bedNum: Int,
    val localDoctorId: String
)