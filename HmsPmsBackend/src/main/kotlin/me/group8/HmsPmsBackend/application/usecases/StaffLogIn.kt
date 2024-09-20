package me.group8.HmsPmsBackend.application.usecases

import me.group8.HmsPmsBackend.domain.staff.entities.GenericStaff
import me.group8.HmsPmsBackend.utils.StaffType

interface StaffLogIn {
    fun getStaffObj(staffId: String): GenericStaff?
    fun getStaffType(staffId: String): StaffType?
}