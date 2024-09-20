package me.group8.HmsPmsBackend.application.controller.payload

data class DischargeRequest (val patientId: String,
                             val reasonForDischarge: String,
                             val divisionId: String)