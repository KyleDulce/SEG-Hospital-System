package me.group8.HmsPmsBackend.application.db.postgres.entities

import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import me.group8.HmsPmsBackend.application.db.postgres.entities.id.MedicalDivisionBedId

@Entity
@Table(name = "medical_division_bed")
class MedicalDivisionBedEntity(
    @EmbeddedId
    var medicalDivisionBedId: MedicalDivisionBedId
) {
    constructor() : this(MedicalDivisionBedId()) {

    }
}