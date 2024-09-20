package me.group8.HmsPmsBackend.domain.staff.facade.impl

import me.group8.HmsPmsBackend.application.dtos.queries.ChargeNurseCreateDto
import me.group8.HmsPmsBackend.application.dtos.queries.DoctorCreateDto
import me.group8.HmsPmsBackend.application.dtos.queries.StaffMemberCreateDto
import me.group8.HmsPmsBackend.application.services.DomainEventEmitter
import me.group8.HmsPmsBackend.application.services.HRService
import me.group8.HmsPmsBackend.domain.staff.entities.ChargeNurse
import me.group8.HmsPmsBackend.domain.staff.entities.Doctor
import me.group8.HmsPmsBackend.domain.staff.entities.GenericStaff
import me.group8.HmsPmsBackend.domain.staff.services.StaffNotificationService
import me.group8.HmsPmsBackend.domain.staff.events.StaffCreated
import me.group8.HmsPmsBackend.domain.staff.facade.StaffFacade
import me.group8.HmsPmsBackend.domain.staff.repositories.StaffRepository
import me.group8.HmsPmsBackend.domain.staff.factories.StaffFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class StaffFacadeImpl (val staffRepository: StaffRepository,
                       val staffFactory: StaffFactory,
                       val eventEmitter: DomainEventEmitter,
                       val notificationService: StaffNotificationService,
                       val hrService: HRService): StaffFacade {
    override fun registerDoctor(doctorInfo: DoctorCreateDto): Boolean {
        val employeeId = doctorInfo.staffInfo.employeeId;
        val employeeExists = staffRepository.doesEmployeeExist(employeeId);
        if (!employeeExists) {
            notificationService.sendRegistrationNotification(
                    "Staff with ID: " + doctorInfo.staffInfo.employeeId + " does not exist"
            )
            val doctor = staffFactory.registerDoctor(doctorInfo);
            staffRepository.saveDoctor(doctor);
            eventEmitter.emit(
                    StaffCreated(UUID.randomUUID().toString(),
                            doctor.employeeId)
            )
            return true;
        } else {
            notificationService.sendRegistrationNotification(
                    "Information provided is not valid"
            )
            return false;
        }
    }

    override fun registerChargeNurse(nurseInfo: ChargeNurseCreateDto): Boolean {
        val employeeId = nurseInfo.staffInfo.employeeId;
        val employeeExists = staffRepository.doesEmployeeExist(employeeId);
        if(!employeeExists) {
            notificationService.sendRegistrationNotification(
                    "Staff with ID: " + nurseInfo.staffInfo.employeeId + " does not exist"
            )

            val nurse = staffFactory.registerChargeNurse(nurseInfo);
            staffRepository.saveNurse(nurse);
            eventEmitter.emit(
                    StaffCreated(UUID.randomUUID().toString(),
                            nurse.employeeId)
            )
            return true;
        }
        else {
            notificationService.sendRegistrationNotification(
                    "Information provided is not valid"
            )
            return false;
        }
    }

    override fun registerGenericStaff(staffInfo: StaffMemberCreateDto): Boolean {
        val employeeId = staffInfo.employeeId;
        val employeeExists = staffRepository.doesEmployeeExist(employeeId);
        if(!employeeExists) {
            notificationService.sendRegistrationNotification(
                    "Staff with ID: " + staffInfo.employeeId + " does not exist"
            )

            val staff = staffFactory.registerGenericStaff(staffInfo);
            staffRepository.saveStaff(staff);
            eventEmitter.emit(
                    StaffCreated(UUID.randomUUID().toString(),
                            staff.employeeId)
            )
            return true;
        }

        else {
            notificationService.sendRegistrationNotification(
                    "Information provided is not valid"
            )
                return false;
        }
    }

    override fun notifyStaffRejectedAdmission(chargeNurseId: String, patientId: String): Void? {
        val nurse = staffRepository.findNurse(chargeNurseId);
        if(nurse != null){
            notificationService.sendRejectionNotification(
                    " " + chargeNurseId + ", " + patientId + " was rejected from their admission"
            )

        }


        return null;
    }
    override fun validateInfoStaff(employeeId: String): Boolean{
         return hrService.doesEmployeeExist(employeeId)
    }

    override fun doesHaveUpdatePermission(employeeId: String): Boolean {
        val staff = staffRepository.findstaff(employeeId)
        return staff != null && staff.modifyPermission
    }

    override fun getStaffInfo(employeeId: String): GenericStaff? {
        return staffRepository.findstaff(employeeId)
    }

    override fun getDoctorInfo(employeeId: String): Doctor? {
        return staffRepository.findDoctor(employeeId);
    }

    override fun getNurseInfo(employeeId: String): ChargeNurse? {
        return staffRepository.findNurse(employeeId);
    }

    override fun getStaffName(employeeId: String): String? {
        val staff = staffRepository.findstaff(employeeId)?: return null
        return "${staff.firstName} ${staff.lastName}"
    }
}