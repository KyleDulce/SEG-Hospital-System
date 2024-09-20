package me.group8.HmsPmsBackend.application.dtos.queries

import java.time.LocalTime

data class MedAdminInfoCreateDto (val timeOfDay: LocalTime,
                                 val numUnits: Integer)

