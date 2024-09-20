package me.group8.HmsPmsBackend.application.db.postgres.entities

import jakarta.annotation.Nullable
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import me.group8.HmsPmsBackend.utils.TypeUtils
import java.sql.Date

@Entity
@Table(name = "staff")
class StaffEntity(
    @Id
    @Column
    var employeeId: String,
    @Column
    var firstName: String,
    @Column
    var lastName: String,
    @Column
    var addressId: String,
    @Column
    var telephone: Long,
    @Column
    var dateOfBirth: Date,
    @Column
    var userName: String,
    @Column(name = "staff_password")
    var password: String,
    @Column
    @Nullable
    var teleExtension: Int,
    @Column
    var modifyPermission: Boolean
) {
    constructor() : this("", "", "", "", 0, TypeUtils.blankSqlDate(), "", "", 0, false) {

    }
}