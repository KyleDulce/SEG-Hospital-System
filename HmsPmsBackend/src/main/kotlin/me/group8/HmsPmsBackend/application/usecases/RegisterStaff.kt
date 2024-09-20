package me.group8.HmsPmsBackend.application.usecases

import me.group8.HmsPmsBackend.application.dtos.queries.ChargeNurseCreateDto
import me.group8.HmsPmsBackend.application.dtos.queries.DoctorCreateDto
import me.group8.HmsPmsBackend.application.dtos.queries.StaffMemberCreateDto

interface RegisterStaff {
    fun registerDoctor(doctorInfo: DoctorCreateDto): Boolean
    fun registerChargeNurse(nurseInfo: ChargeNurseCreateDto): Boolean
    fun registerGenericStaff(staffInfo: StaffMemberCreateDto): Boolean
}