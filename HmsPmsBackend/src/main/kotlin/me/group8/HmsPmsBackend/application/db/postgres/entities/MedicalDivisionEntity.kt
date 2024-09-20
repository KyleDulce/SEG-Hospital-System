package me.group8.HmsPmsBackend.application.db.postgres.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "medical_division")
class MedicalDivisionEntity(
    @Id
    @Column
    var identifier: String,
    @Column(name = "div_name")
    var name: String,
    @Column
    var chargeNurseId: String,
    @Column(name = "div_location")
    var location: String,
    @Column
    var numBeds: Int,
    @Column
    var teleExt: Int,
    @Column(name = "div_status")
    var status: String,
    @Column
    var occupiedBeds: Int
) {
    constructor() : this("", "", "", "", 0, 0, "", 0) {

    }
}