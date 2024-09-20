package me.group8.HmsPmsBackend.application.db.postgres.entities

import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "medical_division_admit_req")
class MedicalDivisionAdmitRequest(
    @Column(name="med_identifier")
    var medIddentifier: String,
    @Id
    @Column(name="pat_identifier")
    var patIdentifier: String
) {
    constructor() : this("", "") {

    }
}