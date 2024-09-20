package me.group8.HmsPmsBackend.application.db.postgres.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "charge_nurse")
class ChargeNurseEntity(
    @Id
    @Column
    var employeeId: String,
    @Column
    var bipperExtension: Int
) {
    constructor() : this("", 0) {

    }
}