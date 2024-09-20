package me.group8.HmsPmsBackend.application.dtos.queries

data class DoctorCreateDto (val staffInfo: StaffMemberCreateDto,
                      val divisionName: String)

