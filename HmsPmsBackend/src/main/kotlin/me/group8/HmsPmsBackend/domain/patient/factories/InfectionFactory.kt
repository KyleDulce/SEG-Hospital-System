package me.group8.HmsPmsBackend.domain.patient.factories

import me.group8.HmsPmsBackend.domain.patient.entities.Infection
import org.springframework.stereotype.Service
import java.util.*

@Service
class InfectionFactory {
    fun create(infectionId: String,
               patientId: String,
               startDate: Date,
               endDate: Date,
               name: String): Infection {
        return Infection(
                UUID.randomUUID().toString(),
                patientId,
                startDate,
                endDate,
                name
        )
    }
}