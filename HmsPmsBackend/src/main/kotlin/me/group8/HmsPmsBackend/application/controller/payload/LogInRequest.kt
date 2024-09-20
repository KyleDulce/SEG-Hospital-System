package me.group8.HmsPmsBackend.application.controller.payload

data class LogInRequest(
    val employeeId: String,
    val password: String
)