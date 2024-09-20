package me.group8.HmsPmsBackend.application.controller.payload

import me.group8.HmsPmsBackend.application.dtos.queries.DivisionBedEntryDto
import me.group8.HmsPmsBackend.application.dtos.queries.DivisionCreateDto

data class GetDivisionResponse(
    val division: DivisionCreateDto,
    val beds: Array<DivisionBedEntryDto>,
    val chargeNurseName: String
)
