package me.group8.HmsPmsBackend.application.dtos.queries

import java.util.Date

data class PatientCreateDto (val firstName: String,
                       val lastName: String,
                       val address: AddressCreateDto,
                       val telephone: Long,
                       val dateOfBirth: Date,
                       val gender: String,
                       val maritalStatus: String,
                       val doctorId: String,
                       val nextOfKin: NextOfKinCreateDto,
                       val govInsuranceNum: Long)
