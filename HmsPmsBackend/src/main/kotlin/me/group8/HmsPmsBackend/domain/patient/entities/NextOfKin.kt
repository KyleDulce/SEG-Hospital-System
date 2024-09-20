package me.group8.HmsPmsBackend.domain.patient.entities

class NextOfKin(
    val firstName: String,
    val lastName: String,
    val relationshipToPatient: String,
    val address: Address,
    val telephone: Long
) {
}