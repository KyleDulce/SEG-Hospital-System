package me.group8.HmsPmsBackend.application.db.postgres.entities

import jakarta.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "access_log")
class LogEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGSERIAL")
    var id: Long,
    @Column(name = "log_time")
    @Temporal(TemporalType.TIMESTAMP)
    var time: Timestamp,
    @Column
    var staffId: String,
    @Column
    var patientId: String
) {
    constructor() : this(0, Timestamp(0), "", "") {

    }
}