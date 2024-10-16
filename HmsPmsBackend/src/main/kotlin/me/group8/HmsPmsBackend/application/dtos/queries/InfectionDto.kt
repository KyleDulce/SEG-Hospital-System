package me.group8.HmsPmsBackend.application.dtos.queries

import java.util.Date

data class InfectionDto (val patientId: String,
                         val startDate: Date,
                         val endDate: Date,
                         val name: String)
