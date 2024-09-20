package me.group8.HmsPmsBackend.application.dtos.queries

data class NextOfKinCreateDto (val firstName: String,
                         val lastName : String,
                         val address: AddressCreateDto,
                         val telephone: Long,
                         val relationship: String)

