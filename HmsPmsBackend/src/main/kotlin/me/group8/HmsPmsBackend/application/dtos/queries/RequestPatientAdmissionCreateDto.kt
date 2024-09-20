package me.group8.HmsPmsBackend.application.dtos.queries

data class RequestPatientAdmissionCreateDto (val divisionId: String,
                                             val patientId: String,
                                             val requestingChargeNurse: String,
                                             val requestRationale: String,
                                             val priority: Int,
                                             val localDoctorID: String)
