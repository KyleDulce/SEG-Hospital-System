package me.group8.HmsPmsBackend.application.db.postgres.entities

import jakarta.persistence.*

@Entity
@Table(name = "address_record")
class AddressEntity (
    @Id
    @Column
    var id: String,
    @Column
    var streetNum: Int,
    @Column
    var streetName: String,
    @Column
    var aptNumber: Int,
    @Column
    var postalCode: String,
    @Column
    var city: String,
    @Column
    var province: String,
    @Column
    var country: String
) {
    constructor() : this("", 0, "", 0, "", "", "", "") {

    }

}