package me.group8.HmsPmsBackend.application.dtos.queries

data class DivisionCreateDto (val identifier: String,
                              val name: String,
                              val chargeNurse: String,
                              val location: String,
                              val numOfBeds: Int,
                              val teleExtension: Int,
                              val status: String,
                              val bedIds: Array<String>,
                              val admitRequests: Array<RequestPatientAdmissionCreateDto>
)
