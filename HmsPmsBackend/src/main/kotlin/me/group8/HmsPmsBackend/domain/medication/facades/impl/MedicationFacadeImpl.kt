package me.group8.HmsPmsBackend.domain.medication.facades.impl

import me.group8.HmsPmsBackend.domain.medication.facades.MedicationFacade
import me.group8.HmsPmsBackend.application.dtos.queries.MedicationCreateDto
import me.group8.HmsPmsBackend.domain.medication.factories.MedicationFactory
import me.group8.HmsPmsBackend.domain.medication.repositories.MedicationRepository
import me.group8.HmsPmsBackend.domain.medication.entities.Medication
import org.springframework.stereotype.Service

@Service
class MedicationFacadeImpl (
    val medicationRepository: MedicationRepository,
    val medicationFactory: MedicationFactory
) : MedicationFacade {
    override fun createPrescription(patientId: String, medicationInfo: MedicationCreateDto): Boolean {

        val medication = medicationFactory.createMedication(patientId, medicationInfo)

        medicationRepository.save(medication)

        return true
    }


    override fun getAllPatientPrescriptions(patientId: String): Array<Medication> {
        val medications = medicationRepository.findAllByPatientId(patientId)

        return medications;
        
    }

}