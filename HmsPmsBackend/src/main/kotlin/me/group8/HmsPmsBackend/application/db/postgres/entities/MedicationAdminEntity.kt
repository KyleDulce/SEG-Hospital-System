package me.group8.HmsPmsBackend.application.db.postgres.entities

import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import me.group8.HmsPmsBackend.application.db.postgres.entities.id.MedicationAdminId

@Entity
@Table(name = "medication_admin")
class MedicationAdminEntity(
    @EmbeddedId
    var medicationAdminId: MedicationAdminId,
    @Column
    var numUnits: Int
) {
    constructor() : this(MedicationAdminId(), 0) {

    }
}