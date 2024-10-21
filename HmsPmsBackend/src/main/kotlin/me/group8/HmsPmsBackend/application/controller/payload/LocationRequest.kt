package me.group8.HmsPmsBackend.application.controller.payload

data class LocationRequest (
        val streetNum: Int,
        val streetName: String,
        val aptNumber: Int,
        val postalCode: String,
        val city: String,
        val province: String,
        val country: String
)