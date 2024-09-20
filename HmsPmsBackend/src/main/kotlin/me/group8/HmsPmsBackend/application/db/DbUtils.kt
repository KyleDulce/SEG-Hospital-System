package me.group8.HmsPmsBackend.application.db

import me.group8.HmsPmsBackend.application.db.postgres.entities.AddressEntity
import me.group8.HmsPmsBackend.domain.patient.entities.Address
import java.util.*

class DbUtils {
    companion object {
        fun entityToAddress(addressEntity: AddressEntity): Address {
            return Address(
                addressEntity.streetNum,
                addressEntity.streetName,
                addressEntity.aptNumber,
                addressEntity.postalCode,
                addressEntity.city,
                addressEntity.province,
                addressEntity.country
            )
        }

        fun addressToEntity(address: Address, id: String? = null): AddressEntity {
            val resultId: String
            if (id == null) {
                resultId = UUID.randomUUID().toString()
            } else {
                resultId = id
            }
            return AddressEntity(
                resultId,
                address.streetNum,
                address.streetName,
                address.aptNumber,
                address.postalCode,
                address.city,
                address.province,
                address.country
            )
        }
    }
}