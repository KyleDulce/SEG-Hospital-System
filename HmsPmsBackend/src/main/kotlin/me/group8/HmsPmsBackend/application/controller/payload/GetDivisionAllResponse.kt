package me.group8.HmsPmsBackend.application.controller.payload

import me.group8.HmsPmsBackend.application.dtos.queries.DivisionEntryDto

data class GetDivisionAllResponse(
    val divisions: Array<DivisionEntryDto>
)