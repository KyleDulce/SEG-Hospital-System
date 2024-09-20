package me.group8.HmsPmsBackend.domain.staff.factories

import me.group8.HmsPmsBackend.application.dtos.queries.ChargeNurseCreateDto
import me.group8.HmsPmsBackend.application.dtos.queries.DoctorCreateDto
import me.group8.HmsPmsBackend.application.dtos.queries.StaffMemberCreateDto
import me.group8.HmsPmsBackend.domain.patient.entities.Address
import me.group8.HmsPmsBackend.domain.staff.entities.ChargeNurse
import me.group8.HmsPmsBackend.domain.staff.entities.Doctor
import me.group8.HmsPmsBackend.domain.staff.entities.GenericStaff
import org.springframework.stereotype.Service

@Service
class StaffFactory {
    fun registerGenericStaff(staffInfo: StaffMemberCreateDto): GenericStaff {
        return GenericStaff(
            staffInfo.firstName,
            staffInfo.lastName,
            Address(
                staffInfo.address.streetNum,
                staffInfo.address.streetName,
                staffInfo.address.aptNumber,
                staffInfo.address.postalCode,
                staffInfo.address.city,
                staffInfo.address.province,
                staffInfo.address.country
            ),
            staffInfo.telephone,
            staffInfo.employeeId,
            staffInfo.userName,
            staffInfo.password,
            staffInfo.telephoneExt,
            staffInfo.dateOfBirth,
            staffInfo.modifyPermission
        );
    }

    fun registerDoctor(doctorInfo: DoctorCreateDto): Doctor {
        return Doctor(
            doctorInfo.staffInfo.firstName,
            doctorInfo.staffInfo.lastName,
            Address(
                doctorInfo.staffInfo.address.streetNum,
                doctorInfo.staffInfo.address.streetName,
                doctorInfo.staffInfo.address.aptNumber,
                doctorInfo.staffInfo.address.postalCode,
                doctorInfo.staffInfo.address.city,
                doctorInfo.staffInfo.address.province,
                doctorInfo.staffInfo.address.country
            ),
            doctorInfo.staffInfo.telephone,
            doctorInfo.staffInfo.employeeId,
            doctorInfo.staffInfo.userName,
            doctorInfo.staffInfo.password,
            doctorInfo.staffInfo.telephoneExt,
            doctorInfo.staffInfo.dateOfBirth,
            doctorInfo.staffInfo.modifyPermission,
            doctorInfo.divisionName
        );
    }

    fun registerChargeNurse(nurseInfo: ChargeNurseCreateDto): ChargeNurse {
        return ChargeNurse(
            nurseInfo.staffInfo.firstName,
            nurseInfo.staffInfo.lastName,
            Address(
                nurseInfo.staffInfo.address.streetNum,
                nurseInfo.staffInfo.address.streetName,
                nurseInfo.staffInfo.address.aptNumber,
                nurseInfo.staffInfo.address.postalCode,
                nurseInfo.staffInfo.address.city,
                nurseInfo.staffInfo.address.province,
                nurseInfo.staffInfo.address.country
            ),
            nurseInfo.staffInfo.telephone,
            nurseInfo.staffInfo.employeeId,
            nurseInfo.staffInfo.userName,
            nurseInfo.staffInfo.password,
            nurseInfo.staffInfo.telephoneExt,
            nurseInfo.staffInfo.dateOfBirth,
            nurseInfo.staffInfo.modifyPermission,
            nurseInfo.bipperExtension
        );
    }
}