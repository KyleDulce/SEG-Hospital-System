package me.group8.HmsPmsBackend.application.dtos.queries

data class DischargePatientCreateDto (val patientId: String,
                                      val reasonForDischarge: String,
                                      val divisionId: String)

