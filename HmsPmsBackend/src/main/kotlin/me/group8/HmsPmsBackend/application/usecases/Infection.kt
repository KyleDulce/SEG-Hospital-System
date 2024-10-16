package me.group8.HmsPmsBackend.application.usecases

import me.group8.HmsPmsBackend.application.dtos.queries.InfectionDto

interface Infection {
    fun addInfection(infection: InfectionDto, employeeId: String): Boolean
    fun updateInfection(infectionId: String, infection: InfectionDto, employeeId: String): Boolean
}