package me.group8.HmsPmsBackend.application.controller.payload

data class RequestAdmissionRequest (
                                    val patientId: String,
                                    val divisionId: String,
                                    val priority: Int,
                                    val requestRationale: String,
                                    val localDoctorId: String,
                                    val requestingChargeNurseId: String)
