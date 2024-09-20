package me.group8.HmsPmsBackend.application.dtos.queries

import java.util.Date


data class StaffMemberCreateDto ( val firstName: String,
                                  val lastName: String,
                                  val address: AddressCreateDto,
                                  val telephone: Long,
                                  val telephoneExt: Int,
                                  val dateOfBirth: Date,
                                  val employeeId: String,
                                  val userName: String,
                                  val password: String,
                                  val modifyPermission: Boolean)

