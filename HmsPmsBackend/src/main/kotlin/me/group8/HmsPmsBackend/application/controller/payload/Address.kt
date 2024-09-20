package me.group8.HmsPmsBackend.application.controller.payload

data class Address (
    val streetNum: Number,
    val streetName: String,
    val aptNumber: Number,
    val postalCode: String,
    val city: String,
    val province: String,
    val country: String
)