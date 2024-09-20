package me.group8.HmsPmsBackend.application.dtos.queries

data class ChargeNurseCreateDto (val staffInfo: StaffMemberCreateDto,
                           val bipperExtension: Int)

