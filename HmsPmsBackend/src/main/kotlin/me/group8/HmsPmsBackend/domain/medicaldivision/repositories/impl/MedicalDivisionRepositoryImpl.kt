package me.group8.HmsPmsBackend.domain.medicaldivision.repositories.impl

import me.group8.HmsPmsBackend.application.db.postgres.entities.AdmitRequestEntity
import me.group8.HmsPmsBackend.application.db.postgres.entities.MedicalDivisionAdmitRequest
import me.group8.HmsPmsBackend.application.db.postgres.entities.MedicalDivisionBedEntity
import me.group8.HmsPmsBackend.application.db.postgres.entities.MedicalDivisionEntity
import me.group8.HmsPmsBackend.application.db.postgres.repository.AdmitRequestTableRepository
import me.group8.HmsPmsBackend.application.db.postgres.repository.MedicalDivisionAdmitRequestRepository
import me.group8.HmsPmsBackend.application.db.postgres.repository.MedicalDivisionBedRepository
import me.group8.HmsPmsBackend.application.db.postgres.repository.MedicalDivisionTableRepository
import me.group8.HmsPmsBackend.domain.medicaldivision.entities.AdmitRequest
import me.group8.HmsPmsBackend.domain.medicaldivision.entities.MedicalDivision
import me.group8.HmsPmsBackend.domain.medicaldivision.repositories.MedicalDivisionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class MedicalDivisionRepositoryImpl(
    private val admitRequestTableRepository: AdmitRequestTableRepository,
    private val medicalDivisionAdmitRequestRepository: MedicalDivisionAdmitRequestRepository,
    private val medicalDivisionBedRepository: MedicalDivisionBedRepository,
    private val medicalDivisionTableRepository: MedicalDivisionTableRepository
): MedicalDivisionRepository {
    override fun find(divisionId: String): MedicalDivision? {
        val medDivEntityOpt = medicalDivisionTableRepository.findById(divisionId)

        if(medDivEntityOpt.isEmpty) {
            return null
        }
        val medId = medDivEntityOpt.get().identifier

        val beds = medicalDivisionBedRepository.findByMedicalDivisionBedId_MedIdentifier(medId)
        val admitReqs = medicalDivisionAdmitRequestRepository.findByMedIddentifier(medId)

        return entityToMedicalDivision(medDivEntityOpt.get(), beds.toTypedArray(), admitReqs.toTypedArray())
    }

    @Transactional(propagation = Propagation.REQUIRED)
    override fun save(divisionId: MedicalDivision): MedicalDivision {

        medicalDivisionTableRepository.save(medicalDivisionToEntity(divisionId))
        //beds do not have to be saved as they cannot be modified

        // remove all unused admit requests and insert new ones
        val admitReqsIdDb = medicalDivisionAdmitRequestRepository.findByMedIddentifier(divisionId.identifier)
        val admitReqsIdCur = getAdmitReqIdMapping(divisionId)

        // all to be removed
        val toRemoveId = admitReqsIdDb
            // if the current does not contain what is in db, remove it
            .filter { admitReq -> !admitReqsIdCur.contains(admitReq) }

        // add to add
        val toAddId = admitReqsIdCur
            // if the db dos not contain what is in cur, add it
            .filter { admitReq -> !admitReqsIdDb.contains(admitReq) }

        // remove ids
        toRemoveId.forEach {
            admitReqId -> medicalDivisionAdmitRequestRepository.delete(admitReqId)
        }

        // remove entities
        toRemoveId.forEach {
                admitReqId -> admitRequestTableRepository.deleteById(admitReqId.patIdentifier)
        }

        // add ids
        toAddId.forEach {
                admitReqId -> medicalDivisionAdmitRequestRepository.save(admitReqId)
        }

        // save all entities
        divisionId.admitReqs
            .map { admitReq -> getAdmitReqEntity(admitReq) }
            .forEach {
                admitReqEnt -> admitRequestTableRepository.save(admitReqEnt)
        }

        return divisionId
    }

    override fun getAll(): Array<MedicalDivision> {
        val medDivs = medicalDivisionTableRepository.findAll()

        if(medDivs.isEmpty()) {
            return arrayOf()
        }

        return medDivs
            .map (fun(div): MedicalDivision {
                val medId = div.identifier

                val beds = medicalDivisionBedRepository.findByMedicalDivisionBedId_MedIdentifier(medId)
                val admitReqs = medicalDivisionAdmitRequestRepository.findByMedIddentifier(medId)

                return entityToMedicalDivision(div, beds.toTypedArray(), admitReqs.toTypedArray())
            })
            .toTypedArray()
    }

    override fun findByChargeNurseId(chargeNurseId: String): MedicalDivision? {
        val medDivOpt = medicalDivisionTableRepository.findByChargeNurseId(chargeNurseId)

        if(medDivOpt.isEmpty) {
            return null
        }

        val medId = medDivOpt.get().identifier

        val beds = medicalDivisionBedRepository.findByMedicalDivisionBedId_MedIdentifier(medId)
        val admitReqs = medicalDivisionAdmitRequestRepository.findByMedIddentifier(medId)

        return entityToMedicalDivision(medDivOpt.get(), beds.toTypedArray(), admitReqs.toTypedArray())
    }

    override fun findAdmitRequestDivisionByPatientId(patientId: String): String? {
        val request = admitRequestTableRepository.findById(patientId)

        if(request.isEmpty) {
            return null
        }

        val divRelation = medicalDivisionAdmitRequestRepository.findById(patientId)

        if(divRelation.isEmpty) {
            return null
        }
        return divRelation.get().medIddentifier
    }

    private fun entityToMedicalDivision(medicalDivisionEntity: MedicalDivisionEntity,
                                        medicalDivisionBed: Array<MedicalDivisionBedEntity>,
                                        medicalDivisionAdmitRequest: Array<MedicalDivisionAdmitRequest>): MedicalDivision {
        val medDivision = MedicalDivision(
            medicalDivisionEntity.identifier,
            medicalDivisionEntity.name,
            medicalDivisionEntity.chargeNurseId,
            medicalDivisionEntity.location,
            medicalDivisionEntity.numBeds,
            medicalDivisionEntity.teleExt,
            medicalDivisionEntity.status,
            medicalDivisionBed
                .map { medBed -> medBed.medicalDivisionBedId.bed_identifier }
                .toTypedArray()
        )

        medDivision.admitReqs.addAll(
            medicalDivisionAdmitRequest
                .map { admitReqDivEntity ->
                    admitRequestTableRepository.findById(admitReqDivEntity.patIdentifier) }
                .filter {admitReqEntitiyOpt -> admitReqEntitiyOpt.isPresent}
                .map { admitReqOpt -> admitReqEntityToAdmitReq(admitReqOpt.get()) }
        )

        return medDivision;
    }

    private fun medicalDivisionToEntity(medicalDivision: MedicalDivision): MedicalDivisionEntity {
        return MedicalDivisionEntity(
            medicalDivision.identifier,
            medicalDivision.name,
            medicalDivision.chargeNurseId,
            medicalDivision.location,
            medicalDivision.numBeds,
            medicalDivision.teleExt,
            medicalDivision.status,
                medicalDivision.occupiedBeds
        )
    }

    private fun getAdmitReqEntity(admitReq: AdmitRequest): AdmitRequestEntity {
        return AdmitRequestEntity(
            admitReq.patientId,
            admitReq.requestingChargeNurseId,
            admitReq.priority,
            admitReq.rationale,
                admitReq.localDoctorId
        )
    }

    private fun getAdmitReqIdMapping(medicalDivision: MedicalDivision): Array<MedicalDivisionAdmitRequest> {
        return medicalDivision.admitReqs
            .map { admitReq ->
                MedicalDivisionAdmitRequest(
                    medicalDivision.identifier,
                    admitReq.patientId
                )
            }.toTypedArray()
    }

    private fun admitReqEntityToAdmitReq(admitRequestEntity: AdmitRequestEntity): AdmitRequest {
        return AdmitRequest(
            admitRequestEntity.patientId,
            admitRequestEntity.requestingChargeNurseId,
            admitRequestEntity.priority,
            admitRequestEntity.rationale,
                admitRequestEntity.localDoctorId
        );
    }
}