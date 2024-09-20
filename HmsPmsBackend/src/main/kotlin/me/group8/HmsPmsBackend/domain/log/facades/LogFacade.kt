package me.group8.HmsPmsBackend.domain.log.facades

import java.util.*

interface LogFacade {
    fun logAccess(time: Date, staffId: String, patientId: String)
}