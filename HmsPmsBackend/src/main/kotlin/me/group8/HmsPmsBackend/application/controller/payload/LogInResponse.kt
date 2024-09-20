package me.group8.HmsPmsBackend.application.controller.payload

data class LogInResponse(
    val token: String,
    val username: String,
    val userType: String
)