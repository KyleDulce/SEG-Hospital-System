package me.group8.HmsPmsBackend.application.db.postgres.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "hr_employees")
class HREmployeeEntity(
    @Id
    @Column
    var empId: String
) {
    constructor() : this("") {

    }
}