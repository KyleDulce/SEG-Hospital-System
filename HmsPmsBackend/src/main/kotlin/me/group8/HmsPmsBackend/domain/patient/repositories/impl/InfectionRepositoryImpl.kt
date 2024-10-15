package me.group8.HmsPmsBackend.domain.patient.repositories.impl

import me.group8.HmsPmsBackend.application.db.postgres.entities.InfectionEntity
import me.group8.HmsPmsBackend.application.db.postgres.repository.InfectionTableRepository
import me.group8.HmsPmsBackend.domain.patient.entities.Infection
import me.group8.HmsPmsBackend.domain.patient.repositories.InfectionRepository
import org.springframework.stereotype.Service

@Service
class InfectionRepositoryImpl (
        private val infectionTableRepository: InfectionTableRepository
): InfectionRepository {
    override fun save(infection: Infection): Infection {
        infectionTableRepository.save(infectionToEntity(infection))
        return infection
    }

    override fun find(infectionId: String): Infection? {
        val infectionOpt = infectionTableRepository.findById(infectionId)

        if(infectionOpt.isEmpty) {
            return null
        }
        return entityToInfection(infectionOpt.get())
    }

    override fun findAllByPatientId(patientId: String): Array<Infection> {
        val infections = infectionTableRepository.findByPatientId(patientId)

        return infections
                .map { infection -> entityToInfection(infection) }
                .toTypedArray()
    }

    private fun infectionToEntity(infection: Infection): InfectionEntity {
        return InfectionEntity(
                infection.infectionId,
                infection.patientId,
                infection.startDate,
                infection.endDate,
                infection.name
        )
    }

    private fun entityToInfection(entity: InfectionEntity): Infection {
        return Infection(
                entity.id,
                entity.patientId,
                entity.startDate,
                entity.endDate,
                entity.name
        )
    }
}