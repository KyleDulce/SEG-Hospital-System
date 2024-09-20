package me.group8.HmsPmsBackend.domain.staff.entities

import me.group8.HmsPmsBackend.application.dtos.queries.AddressCreateDto
import me.group8.HmsPmsBackend.domain.patient.entities.Address
import java.util.Date

open class GenericStaff (val firstName: String,
                    val lastName: String,
                    val address: Address,
                    val telephone: Long,
                    val employeeId: String,
                    val userName: String,
                    val password: String,
                    val teleExtension: Int,
                    val dateOfBirth: Date,
                    val modifyPermission: Boolean) {
}

