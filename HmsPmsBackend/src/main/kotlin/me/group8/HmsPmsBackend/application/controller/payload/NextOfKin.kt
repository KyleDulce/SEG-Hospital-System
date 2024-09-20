package me.group8.HmsPmsBackend.application.controller.payload

data class NextOfKin (
    val firstName: String,
    val lastName: String,
    val relationshipToPatient: String,
    val telephone: Long,
    val address: Address
)