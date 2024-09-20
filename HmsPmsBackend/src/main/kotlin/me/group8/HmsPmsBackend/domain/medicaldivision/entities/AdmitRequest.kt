package me.group8.HmsPmsBackend.domain.medicaldivision.entities

class AdmitRequest(
    val patientId: String,
    val requestingChargeNurseId: String,
    val priority: Int,
    val rationale: String,
    val localDoctorId: String
) {
}