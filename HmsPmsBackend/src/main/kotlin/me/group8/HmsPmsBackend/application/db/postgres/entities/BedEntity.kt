package me.group8.HmsPmsBackend.application.db.postgres.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "bed")
class BedEntity(
    @Id
    @Column
    var identifier: String,
    @Column
    var roomNum: Int,
    @Column
    var bedNum: Int,
    @Column
    var isAvailable: Boolean
) {
    constructor() : this("", 0, 0, false) {

    }
}