package me.group8.HmsPmsBackend.application.usecases

import me.group8.HmsPmsBackend.application.dtos.queries.RequestPatientAdmissionCreateDto
import java.util.*

interface RequestPatientAdmission {
    fun requestPatientAdmission(requestAdmission: RequestPatientAdmissionCreateDto): Boolean
}