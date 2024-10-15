package me.group8.HmsPmsBackend.application.db.postgres.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import me.group8.HmsPmsBackend.utils.TypeUtils

@Entity
@Table(name = "infection")
class InfectionEntity(
        @Id
        @Column
        var id: String,
        @Column
        var patientId: String,
        @Column(name = "infection_start_date")
        var startDate: java.util.Date,
        @Column(name = "infection_end_date")
        var endDate: java.util.Date,
        @Column
        var name: String,
) {
    constructor() : this("", "", TypeUtils.blankSqlDate(), TypeUtils.blankSqlDate(), "") {

    }
}