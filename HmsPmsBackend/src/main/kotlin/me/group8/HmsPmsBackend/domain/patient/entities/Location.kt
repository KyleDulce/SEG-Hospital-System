package me.group8.HmsPmsBackend.domain.patient.entities

import me.group8.HmsPmsBackend.application.dtos.queries.AddressCreateDto

class Location (
        val locationId: String,
        var streetNum: Int,
        var streetName: String,
        var aptNumber: Int,
        var postalCode: String,
        var city: String,
        var province: String,
        var country: String
) {
    fun updateInfo(updateInfo: AddressCreateDto) {
        this.streetNum = updateInfo.streetNum
        this.streetName = updateInfo.streetName
        this.aptNumber = updateInfo.aptNumber
        this.postalCode = updateInfo.postalCode
        this.city = updateInfo.city
        this.province = updateInfo.province
        this.country = updateInfo.country
    }
}