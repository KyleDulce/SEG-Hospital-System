package me.group8.HmsPmsBackend.domain.staff.repositories.impl

import me.group8.HmsPmsBackend.application.db.DbUtils
import me.group8.HmsPmsBackend.application.db.postgres.entities.AddressEntity
import me.group8.HmsPmsBackend.application.db.postgres.entities.ChargeNurseEntity
import me.group8.HmsPmsBackend.application.db.postgres.entities.DoctorEntity
import me.group8.HmsPmsBackend.application.db.postgres.entities.StaffEntity
import me.group8.HmsPmsBackend.application.db.postgres.repository.AddressTableRepository
import me.group8.HmsPmsBackend.application.db.postgres.repository.ChargeNurseTableRepository
import me.group8.HmsPmsBackend.application.db.postgres.repository.DoctorTableRepository
import me.group8.HmsPmsBackend.application.db.postgres.repository.StaffTableRepository
import me.group8.HmsPmsBackend.domain.staff.entities.ChargeNurse
import me.group8.HmsPmsBackend.domain.staff.entities.Doctor
import me.group8.HmsPmsBackend.domain.staff.entities.GenericStaff
import me.group8.HmsPmsBackend.domain.staff.repositories.StaffRepository
import me.group8.HmsPmsBackend.utils.TypeUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class StaffRepositoryImpl(
    private val staffTableRepository: StaffTableRepository,
    private val chargeNurseTableRepository: ChargeNurseTableRepository,
    private val doctorTableRepository: DoctorTableRepository,
    private val addressTableRepository: AddressTableRepository
): StaffRepository {
    override fun doesEmployeeExist(employeeId: String): Boolean {
        return staffTableRepository.existsById(employeeId)
    }

    override fun findstaff(employeeId: String): GenericStaff? {
        val staffEntityOpt = staffTableRepository.findById(employeeId)
        if(staffEntityOpt.isEmpty) {
            return null
        }

        val addressEntityOpt = addressTableRepository.findById(staffEntityOpt.get().addressId)
        if(addressEntityOpt.isEmpty) {
            System.err.println("Error! Staff without address!")
            return null
        }

        return entityToStaff(staffEntityOpt.get(), addressEntityOpt.get())
    }

    override fun findDoctor(employeeId: String): Doctor? {
        val staffObj = findstaff(employeeId) ?: return null
        val doctorEntityOpt = doctorTableRepository.findById(employeeId)
        if(doctorEntityOpt.isEmpty) {
            return null
        }

        return entityToDoctor(staffObj, doctorEntityOpt.get())
    }

    override fun findNurse(employeeId: String): ChargeNurse? {
        val staffObj = findstaff(employeeId) ?: return null
        val chargeNurseOpt = chargeNurseTableRepository.findById(employeeId)
        if(chargeNurseOpt.isEmpty) {
            return null
        }

        return entityToNurse(staffObj, chargeNurseOpt.get())
    }

    @Transactional(propagation = Propagation.REQUIRED)
    override fun saveStaff(staff: GenericStaff): GenericStaff {
        val existingStaff = staffTableRepository.existsById(staff.employeeId)
        val staffAddress: AddressEntity

        if(existingStaff) {
            val oldStaffEntity = staffTableRepository.findById(staff.employeeId)

            staffAddress = DbUtils.addressToEntity(staff.address, oldStaffEntity.get().addressId)
        } else {
            staffAddress = DbUtils.addressToEntity(staff.address)
        }

        val staffEntity = staffToEntity(staff, staffAddress.id)
        staffTableRepository.save(staffEntity)
        addressTableRepository.save(staffAddress)

        return staff
    }

    @Transactional(propagation = Propagation.REQUIRED)
    override fun saveDoctor(doctor: Doctor): Doctor {
        saveStaff(doctor)
        doctorTableRepository.save(doctorToEntity(doctor))
        return doctor
    }

    @Transactional(propagation = Propagation.REQUIRED)
    override fun saveNurse(nurse: ChargeNurse): ChargeNurse {
        saveStaff(nurse)
        chargeNurseTableRepository.save(nurseToEntity(nurse))
        return nurse
    }

    private fun entityToStaff(staffEntity: StaffEntity, addressEntity: AddressEntity): GenericStaff {
        return GenericStaff(
            staffEntity.firstName,
            staffEntity.lastName,
            DbUtils.entityToAddress(addressEntity),
            staffEntity.telephone,
            staffEntity.employeeId,
            staffEntity.userName,
            staffEntity.password,
            staffEntity.teleExtension,
            staffEntity.dateOfBirth,
            staffEntity.modifyPermission
        )
    }

    private fun staffToEntity(staff: GenericStaff, addressId: String): StaffEntity {
        return StaffEntity(
            staff.employeeId,
            staff.firstName,
            staff.lastName,
            addressId,
            staff.telephone,
            TypeUtils.utilDateToSqlDate(staff.dateOfBirth),
            staff.userName,
            staff.password,
            staff.teleExtension,
            staff.modifyPermission
        )
    }

    private fun entityToDoctor(staff: GenericStaff, doctorEntity: DoctorEntity): Doctor {
        return Doctor(
            staff.firstName,
            staff.lastName,
            staff.address,
            staff.telephone,
            staff.employeeId,
            staff.userName,
            staff.password,
            staff.teleExtension,
            staff.dateOfBirth,
            staff.modifyPermission,
            doctorEntity.divisionName
        )
    }

    private fun doctorToEntity(doctor: Doctor): DoctorEntity {
        return DoctorEntity(
            doctor.employeeId,
            doctor.divisionName
        )
    }

    private fun entityToNurse(staff: GenericStaff, chargeNurseEntity: ChargeNurseEntity): ChargeNurse {
        return ChargeNurse(
            staff.firstName,
            staff.lastName,
            staff.address,
            staff.telephone,
            staff.employeeId,
            staff.userName,
            staff.password,
            staff.teleExtension,
            staff.dateOfBirth,
            staff.modifyPermission,
            chargeNurseEntity.bipperExtension
        )
    }

    private fun nurseToEntity(chargeNurse: ChargeNurse): ChargeNurseEntity {
        return ChargeNurseEntity(
            chargeNurse.employeeId,
            chargeNurse.bipperExtension
        )
    }
}