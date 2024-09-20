package me.group8.HmsPmsBackend.domain.medication.repositories.impl

import me.group8.HmsPmsBackend.application.db.postgres.entities.MedicationAdminEntity
import me.group8.HmsPmsBackend.application.db.postgres.entities.MedicationEntity
import me.group8.HmsPmsBackend.application.db.postgres.entities.id.MedicationAdminId
import me.group8.HmsPmsBackend.application.db.postgres.entities.id.MedicationId
import me.group8.HmsPmsBackend.application.db.postgres.repository.MedicationAdministrationTableRepository
import me.group8.HmsPmsBackend.application.db.postgres.repository.MedicationTableRepository
import me.group8.HmsPmsBackend.domain.medication.entities.Medication
import me.group8.HmsPmsBackend.domain.medication.entities.MedicationAdministration
import me.group8.HmsPmsBackend.domain.medication.repositories.MedicationRepository
import me.group8.HmsPmsBackend.utils.TypeUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.stereotype.Repository

@Repository
class MedicationRepositoryImpl(
    private val medicationTableRepository: MedicationTableRepository,
    private val medicationAdministrationTableRepository: MedicationAdministrationTableRepository
): MedicationRepository {
    @Transactional(propagation = Propagation.REQUIRED)
    override fun save(medication: Medication): Medication {
        medicationTableRepository.save(getMedEntity(medication))
        getMedAdminEntities(medication)
            .forEach {
                medAdminEnt -> medicationAdministrationTableRepository.save(medAdminEnt)
            }

        return medication
    }

    private fun getMedEntity(medication: Medication): MedicationEntity {
        return MedicationEntity(
            MedicationId(
                medication.identifier,
                medication.patientId,
                TypeUtils.utilDateToSqlDate(medication.startDate),
                TypeUtils.utilDateToSqlDate(medication.endDate)
            ),
            medication.name,
            medication.unitByDay,
            medication.adminByDay,
            medication.methodOfAdministration,
        )
    }

    private fun getMedAdminEntities(medication: Medication): Array<MedicationAdminEntity> {
        return medication.medAdmins
            .map { medAdmin -> getMedAdminEntity(medAdmin, medication) }
            .toTypedArray()
    }

    private fun getMedAdminEntity(medicationAdministration: MedicationAdministration, medication: Medication): MedicationAdminEntity {
        return MedicationAdminEntity(
            MedicationAdminId(
                medication.identifier,
                medication.patientId,
                TypeUtils.utilDateToSqlDate(medication.startDate),
                TypeUtils.utilDateToSqlDate(medication.endDate),
                TypeUtils.utilDateToSqlDate(medicationAdministration.time)
            ),
            medicationAdministration.numUnits
        )
    }

    override fun findAllByPatientId(patientId: String): Array<Medication> {
        val medications = medicationTableRepository.findByMedicationIdPatientId(patientId)


        return medications
            .map { medication -> entityToMedication(medication) }
            .toTypedArray()
    }


    private fun entityToMedication(entity: MedicationEntity): Medication {
        return Medication(
            entity.medicationId.patientId,
            entity.medicationId.medication_num,
            entity.name,
            entity.unitByDay,
            entity.adminByDay,
            entity.methodOfAdmin,
            entity.medicationId.startDate,
            entity.medicationId.endDate
        )
    }

}

