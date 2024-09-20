package me.group8.HmsPmsBackend.domain.staff.repositories

import me.group8.HmsPmsBackend.domain.staff.entities.ChargeNurse
import me.group8.HmsPmsBackend.domain.staff.entities.Doctor
import me.group8.HmsPmsBackend.domain.staff.entities.GenericStaff

interface StaffRepository {
        fun doesEmployeeExist(employeeId: String):Boolean
        fun findstaff(employeeId: String): GenericStaff?
        fun findDoctor(employeeId: String): Doctor?
        fun findNurse(employeeId: String): ChargeNurse?
        fun saveStaff(staff: GenericStaff): GenericStaff
        fun saveDoctor(doctor: Doctor): Doctor
        fun saveNurse(nurse: ChargeNurse): ChargeNurse
}

