package me.group8.HmsPmsBackend.application.dtos.queries

import java.util.Date

data class MedicationCreateDto  (val drugNumber : Long,
                                 val drugName: String,
                                 val unitsByDay: Int,
                                 val numAdminPerDay: Int,
                                 val startDate: Date,
                                 val endDate: Date,
                                 val methodOfAdmin: String)


