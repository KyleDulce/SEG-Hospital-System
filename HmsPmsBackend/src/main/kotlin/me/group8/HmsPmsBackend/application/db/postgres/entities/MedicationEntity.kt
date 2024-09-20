package me.group8.HmsPmsBackend.application.db.postgres.entities

import jakarta.persistence.*
import me.group8.HmsPmsBackend.application.db.postgres.entities.id.MedicationId
import me.group8.HmsPmsBackend.utils.TypeUtils
import java.sql.Date

@Entity
@Table(name = "medication")
class MedicationEntity (
    @EmbeddedId
    var medicationId: MedicationId,
    @Column(name = "med_name")
    var name: String,
    @Column
    var unitByDay: Int,
    @Column
    var adminByDay: Int,
    @Column
    var methodOfAdmin: String
) {
    constructor() : this( MedicationId(), "", 0, 0, "") {

    }
}