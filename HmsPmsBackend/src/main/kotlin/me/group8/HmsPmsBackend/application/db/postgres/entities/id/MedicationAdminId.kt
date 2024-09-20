package me.group8.HmsPmsBackend.application.db.postgres.entities.id

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import me.group8.HmsPmsBackend.utils.TypeUtils
import java.io.Serializable
import java.sql.Date

@Embeddable
class MedicationAdminId(
    @Column
    var medication_num: Long,
    @Column
    var patientId: String,
    @Column(name = "med_start_date")
    var startDate: Date,
    @Column(name = "med_end_date")
    var endDate: Date,
    @Column(name = "admin_time")
    var time: Date
): Serializable {
    constructor() : this(0, "", TypeUtils.blankSqlDate(), TypeUtils.blankSqlDate(), TypeUtils.blankSqlDate()) {

    }
}