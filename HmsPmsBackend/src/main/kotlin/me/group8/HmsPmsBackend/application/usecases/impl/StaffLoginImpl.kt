package me.group8.HmsPmsBackend.application.usecases.impl

import me.group8.HmsPmsBackend.application.usecases.StaffLogIn
import me.group8.HmsPmsBackend.domain.staff.entities.GenericStaff
import me.group8.HmsPmsBackend.domain.staff.facade.StaffFacade
import me.group8.HmsPmsBackend.utils.StaffType
import org.springframework.stereotype.Service

@Service
class StaffLoginImpl(
    val staffFacade: StaffFacade
): StaffLogIn {
    override fun getStaffObj(staffId: String): GenericStaff? {
        return staffFacade.getStaffInfo(staffId)
    }

    override fun getStaffType(staffId: String): StaffType? {
        return if(staffFacade.getDoctorInfo(staffId) != null) {
            StaffType.DOCTOR
        } else if (staffFacade.getNurseInfo(staffId) != null) {
            StaffType.CHARGE_NURSE
        } else if (staffFacade.getStaffInfo(staffId) != null) {
            StaffType.GENERIC_STAFF
        } else {
            null
        }
    }
}