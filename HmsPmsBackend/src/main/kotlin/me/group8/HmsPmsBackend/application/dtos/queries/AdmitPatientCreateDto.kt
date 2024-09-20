package me.group8.HmsPmsBackend.application.dtos.queries

data class AdmitPatientCreateDto(val divisionId: String,
                                 val patientId: String,
                                 val roomNum: Int,
                                 val bedNum: Int,
                                 val privateInsuranceNum: Long,
                                 val localDoctorId: String)
