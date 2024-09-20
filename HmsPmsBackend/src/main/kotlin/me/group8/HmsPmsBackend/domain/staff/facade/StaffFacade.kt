package me.group8.HmsPmsBackend.domain.staff.facade

import me.group8.HmsPmsBackend.application.db.postgres.entities.StaffEntity
import me.group8.HmsPmsBackend.application.dtos.queries.ChargeNurseCreateDto
import me.group8.HmsPmsBackend.application.dtos.queries.DoctorCreateDto
import me.group8.HmsPmsBackend.application.dtos.queries.StaffMemberCreateDto
import me.group8.HmsPmsBackend.domain.staff.entities.ChargeNurse
import me.group8.HmsPmsBackend.domain.staff.entities.Doctor
import me.group8.HmsPmsBackend.domain.staff.entities.GenericStaff

interface StaffFacade {

    fun registerDoctor(doctorInfo: DoctorCreateDto): Boolean
    fun registerChargeNurse(nurseInfo: ChargeNurseCreateDto): Boolean
    fun registerGenericStaff(staffInfo: StaffMemberCreateDto): Boolean
    fun notifyStaffRejectedAdmission(chargeNurseId: String, patientId: String ): Void?
    fun validateInfoStaff(employeeId: String): Boolean
    fun doesHaveUpdatePermission(employeeId: String): Boolean
    fun getStaffInfo(employeeId: String): GenericStaff?
    fun getDoctorInfo(employeeId: String): Doctor?
    fun getNurseInfo(employeeId: String): ChargeNurse?
    fun getStaffName(employeeId: String): String?
}