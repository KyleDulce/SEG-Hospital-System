package me.group8.HmsPmsBackend.application.db.postgres.entities.id

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
class MedicalDivisionBedId(
    @Column(name = "med_identifier")
    var medIdentifier: String,
    @Column
    var bed_identifier: String
): Serializable {
    constructor() : this("", "") {

    }
}