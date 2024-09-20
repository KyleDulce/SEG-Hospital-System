package me.group8.HmsPmsBackend.application.db.postgres.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import me.group8.HmsPmsBackend.utils.TypeUtils
import java.sql.Date

@Entity
@Table(name = "patient")
class PatientEntity(
    @Id
    @Column
    var patientId: String,
    @Column
    var govInsuranceNum: Long,
    @Column
    var firstName: String,
    @Column
    var lastName: String,
    @Column
    var addressId: String,
    @Column
    var telephone: Long,
    @Column
    var dateOfBirth: Date,
    @Column
    var gender: String,
    @Column
    var martialStatus: String,
    @Column
    var extDoctorId: String
) {
    constructor() : this("", 0, "", "", "", 0, TypeUtils.blankSqlDate(),
    "", "", "") {

    }
}