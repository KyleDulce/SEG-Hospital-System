package me.group8.HmsPmsBackend.application.usecases.impl

import me.group8.HmsPmsBackend.application.dtos.queries.ChargeNurseCreateDto
import me.group8.HmsPmsBackend.application.dtos.queries.DoctorCreateDto
import me.group8.HmsPmsBackend.application.dtos.queries.StaffMemberCreateDto
import me.group8.HmsPmsBackend.application.usecases.RegisterStaff
import me.group8.HmsPmsBackend.domain.staff.facade.StaffFacade
import org.springframework.stereotype.Service

@Service
class RegisterStaffImpl(
        val staffFacade: StaffFacade,
): RegisterStaff {
    override fun registerDoctor(doctorInfo: DoctorCreateDto): Boolean {
        if(staffFacade.validateInfoStaff(doctorInfo.staffInfo.employeeId)) {
            return staffFacade.registerDoctor(doctorInfo)
        }
        else {
            return false;
        }

    }

    override fun registerChargeNurse(nurseInfo: ChargeNurseCreateDto): Boolean {
        if(staffFacade.validateInfoStaff(nurseInfo.staffInfo.employeeId)) {
            return staffFacade.registerChargeNurse(nurseInfo)
        }
        else{
            return false;
        }

    }

    override fun registerGenericStaff(staffInfo: StaffMemberCreateDto): Boolean {
        if(staffFacade.validateInfoStaff(staffInfo.employeeId)) {
            return staffFacade.registerGenericStaff(staffInfo)
        }
        else{
            return false;
        }
    }
}
