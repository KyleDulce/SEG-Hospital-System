package me.group8.HmsPmsBackend.application.usecases

import me.group8.HmsPmsBackend.application.dtos.queries.DivisionBedEntryDto
import me.group8.HmsPmsBackend.application.dtos.queries.DivisionCreateDto
import me.group8.HmsPmsBackend.application.dtos.queries.DivisionEntryDto

interface VisualizeDivision {
    fun getDivision(divisionId: String): DivisionCreateDto?
    fun getBeds(bedIs: Array<String>): Array<DivisionBedEntryDto>
    fun getDivisions(): Array<DivisionEntryDto>
    fun getChargeNurseName(chargeNurseId: String): String?
}