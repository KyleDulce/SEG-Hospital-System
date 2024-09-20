package me.group8.HmsPmsBackend.application.db.postgres.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "patient_next_of_kin")
class NextOfKinEntity(
    @Id
    @Column
    var patientId: String,
    @Column
    var firstName: String,
    @Column
    var lastName: String,
    @Column
    var relationship: String,
    @Column
    var addressId: String,
    @Column
    var telephone: Long
) {
    constructor() : this("", "", "", "", "", 0) {

    }
}