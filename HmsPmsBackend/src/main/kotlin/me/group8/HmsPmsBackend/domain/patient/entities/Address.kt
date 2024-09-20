package me.group8.HmsPmsBackend.domain.patient.entities

class Address(
    val streetNum: Int,
    val streetName: String,
    val aptNumber: Int,
    val postalCode: String,
    val city: String,
    val province: String,
    val country: String
) {
}