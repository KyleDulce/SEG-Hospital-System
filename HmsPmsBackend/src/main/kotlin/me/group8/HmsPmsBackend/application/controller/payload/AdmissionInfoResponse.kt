package me.group8.HmsPmsBackend.application.controller.payload

data class AdmissionInfoResponse(
    val recordId: String,
    val patientId: String,
    val localDoctorId: String,
    val divisionId: String,
    val privateInsuranceNumber: Long,
    val dischargeDate: Long,
    val roomNum: Int,
    val bedNum: Int
)