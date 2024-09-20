package me.group8.HmsPmsBackend.application.db.postgres.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "admit_request")
class AdmitRequestEntity(
    @Id
    @Column
    var patientId: String,
    @Column(name="requesting_charge_nurse_id")
    var requestingChargeNurseId: String,
    @Column(name = "admit_priority")
    var priority: Int,
    @Column
    var rationale: String,
    @Column(name="local_doctor_id")
    var localDoctorId: String
) {
    constructor() : this("", "", 0, "", "") {

    }
}