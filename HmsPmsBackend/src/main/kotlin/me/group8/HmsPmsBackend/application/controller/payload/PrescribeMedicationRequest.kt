package me.group8.HmsPmsBackend.application.controller.payload

import java.util.*

data class PrescribeMedicationRequest(
    val drugNumber : Long,
    val drugName: String,
    val unitsByDay: Int,
    val numAdminPerDay: Int,
    val startDate: Long,
    val endDate: Long,
    val methodOfAdmin: String,
    val patientId: String
)