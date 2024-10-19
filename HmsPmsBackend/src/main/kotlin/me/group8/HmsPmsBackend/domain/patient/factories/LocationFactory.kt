package me.group8.HmsPmsBackend.domain.patient.factories

import me.group8.HmsPmsBackend.application.dtos.queries.AddressCreateDto
import me.group8.HmsPmsBackend.domain.patient.entities.Location
import org.springframework.stereotype.Service
import java.util.*


@Service
class LocationFactory {
    fun createLocation(locationInfo: AddressCreateDto): Location {
        return Location(
                UUID.randomUUID().toString(),
                locationInfo.streetNum,
                locationInfo.streetName,
                locationInfo.aptNumber,
                locationInfo.postalCode,
                locationInfo.city,
                locationInfo.province,
                locationInfo.country,
        )
    }
}