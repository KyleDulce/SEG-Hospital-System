package me.group8.HmsPmsBackend.application.db.postgres.entities

import jakarta.annotation.Nullable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "admission_record")
class AdmissionRecordEntity(
    @Id
    @Column
    var recordId: String,
    @Column
    var patientId: String,
    @Column
    var localDoctorId: String,
    @Column
    var divisionId: String,
    @Column
    var roomNumber: Int,
    @Column
    var bedNumber: Int,
    @Column
    @Nullable
    var privInsuranceNum: Long?,
    @Column
    var isDischarged: Boolean
) {
    constructor() : this("", "", "", "", 0, 0, null, false) {

    }
}