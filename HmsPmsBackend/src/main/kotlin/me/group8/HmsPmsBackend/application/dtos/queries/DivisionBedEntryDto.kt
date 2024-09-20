package me.group8.HmsPmsBackend.application.dtos.queries

data class DivisionBedEntryDto(
    val roomNum: Int,
    val bedNum: Int,
    val isOccupied: Boolean
)