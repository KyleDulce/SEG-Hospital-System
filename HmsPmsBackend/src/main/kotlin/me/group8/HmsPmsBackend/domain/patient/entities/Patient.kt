package me.group8.HmsPmsBackend.domain.patient.entities

import me.group8.HmsPmsBackend.application.dtos.queries.PatientCreateDto
import java.util.Date

class Patient(
    val patientId: String,
    var govInsuranceNum: Long,
    var firstName: String,
    var lastName: String,
    var address: Address,
    var telephone: Long,
    var dateOfBirth: Date,
    var gender: String,
    var martialStatus: String,
    var extDoctorId: String,
    var nextOfKin: NextOfKin
) {
    fun updateInfo(updateInfo: PatientCreateDto) {
        this.govInsuranceNum = updateInfo.govInsuranceNum
        this.firstName = updateInfo.firstName
        this.lastName = updateInfo.lastName
        this.address = Address(
            updateInfo.address.streetNum,
            updateInfo.address.streetName,
            updateInfo.address.aptNumber,
            updateInfo.address.postalCode,
            updateInfo.address.city,
            updateInfo.address.province,
            updateInfo.address.country,
        )
        this.telephone = updateInfo.telephone
        this.dateOfBirth = updateInfo.dateOfBirth
        this.gender = updateInfo.gender
        this.martialStatus = updateInfo.maritalStatus
        this.extDoctorId = updateInfo.doctorId
        this.nextOfKin = NextOfKin(
            updateInfo.nextOfKin.firstName,
            updateInfo.nextOfKin.lastName,
            updateInfo.nextOfKin.relationship,
            Address(
                updateInfo.nextOfKin.address.streetNum,
                updateInfo.nextOfKin.address.streetName,
                updateInfo.nextOfKin.address.aptNumber,
                updateInfo.nextOfKin.address.postalCode,
                updateInfo.nextOfKin.address.city,
                updateInfo.nextOfKin.address.province,
                updateInfo.nextOfKin.address.country,
            ),
            updateInfo.nextOfKin.telephone
        )
    }
}