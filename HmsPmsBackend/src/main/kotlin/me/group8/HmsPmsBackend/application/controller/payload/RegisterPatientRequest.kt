package me.group8.HmsPmsBackend.application.controller.payload

data class RegisterPatientRequest (
    val firstName: String,
    val lastName: String,
    val gender: String,
    val dateOfBirth: String,
    val maritalStatus: String,
    val telephone: Long,
    val extDoctorID: Long,
    val govInsurNum: Long,
    val address: Address,
    val nextOfKin: NextOfKin
)