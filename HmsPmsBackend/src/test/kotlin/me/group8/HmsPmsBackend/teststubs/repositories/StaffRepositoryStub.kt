package me.group8.HmsPmsBackend.teststubs.repositories

import me.group8.HmsPmsBackend.domain.staff.entities.ChargeNurse
import me.group8.HmsPmsBackend.domain.staff.entities.Doctor
import me.group8.HmsPmsBackend.domain.staff.entities.GenericStaff
import me.group8.HmsPmsBackend.domain.staff.repositories.StaffRepository
import javax.print.Doc

class StaffRepositoryStub: StaffRepository {
    val staffMap: MutableMap<String, GenericStaff> = HashMap()

    override fun doesEmployeeExist(employeeId: String): Boolean {
        return staffMap.containsKey(employeeId);
    }

    override fun findstaff(employeeId: String): GenericStaff? {
        return staffMap[employeeId]
    }

    override fun findDoctor(employeeId: String): Doctor? {
        val employee = staffMap[employeeId]
        if (employee != null && employee is Doctor) {
            return employee
        }
        return null
    }

    override fun findNurse(employeeId: String): ChargeNurse? {
        val employee = staffMap[employeeId]
        if (employee != null && employee is ChargeNurse) {
            return employee
        }
        return null
    }

    override fun saveStaff(staff: GenericStaff): GenericStaff {
        staffMap[staff.employeeId] = staff
        return staff
    }

    override fun saveDoctor(doctor: Doctor): Doctor {
        saveStaff(doctor)
        return doctor
    }

    override fun saveNurse(nurse: ChargeNurse): ChargeNurse {
        saveStaff(nurse)
        return nurse
    }
}