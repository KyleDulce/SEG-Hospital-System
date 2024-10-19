package me.group8.HmsPmsBackend.application.db.postgres.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "patient_location")
class PatientLocationEntity (
        @Id
        @Column
        var id: String,
        @Column
        var streetNum: Int,
        @Column
        var streetName: String,
        @Column
        var aptNumber: Int,
        @Column
        var postalCode: String,
        @Column
        var city: String,
        @Column
        var province: String,
        @Column
        var country: String
) {
    constructor() : this("", 0, "", 0, "", "", "", "") {

    }

}