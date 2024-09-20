package me.group8.HmsPmsBackend.domain.staff.entities

import me.group8.HmsPmsBackend.domain.patient.entities.Address
import java.util.*

class ChargeNurse(firstName: String, lastName: String,
                  address: Address,
                  telephone: Long,
                  employeeId: String,
                  userName: String,
                  password: String,
                  teleExtension: Int,
                  dateOfBirth: Date,
                  modifyPermission: Boolean,
                  val bipperExtension: Int):
        GenericStaff(firstName,lastName, address, telephone, employeeId,userName, password, teleExtension, dateOfBirth, modifyPermission) {
}