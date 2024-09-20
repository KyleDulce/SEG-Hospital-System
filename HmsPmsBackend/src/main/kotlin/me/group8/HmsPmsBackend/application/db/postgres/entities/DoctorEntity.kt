package me.group8.HmsPmsBackend.application.db.postgres.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "doctor")
class DoctorEntity(
    @Id
    @Column
    var employeeId: String,
    @Column
    var divisionName: String
) {
    constructor() : this("", "") {

    }
}