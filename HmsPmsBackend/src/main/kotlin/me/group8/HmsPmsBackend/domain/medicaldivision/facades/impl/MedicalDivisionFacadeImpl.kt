package me.group8.HmsPmsBackend.domain.medicaldivision.facades.impl

import me.group8.HmsPmsBackend.domain.medicaldivision.entities.AdmitRequest
import me.group8.HmsPmsBackend.domain.medicaldivision.entities.Bed
import me.group8.HmsPmsBackend.domain.medicaldivision.entities.MedicalDivision
import me.group8.HmsPmsBackend.domain.medicaldivision.facades.MedicalDivisionFacade
import me.group8.HmsPmsBackend.domain.medicaldivision.repositories.BedRepository
import me.group8.HmsPmsBackend.domain.medicaldivision.repositories.MedicalDivisionRepository
import org.springframework.stereotype.Service

@Service
class MedicalDivisionFacadeImpl(
    val medicalDivisionRepository: MedicalDivisionRepository,
    val bedRepository: BedRepository
) : MedicalDivisionFacade {
    override fun isPatientInRequestList(divisionId: String, patientId: String): Boolean {
        val div: MedicalDivision? = medicalDivisionRepository.find(divisionId);

        if(div != null) {
            return div.isPatientInRequestList(patientId)
        }
        return false
    }

    override fun isPatientInRequestList(patientId: String): String? {
        return medicalDivisionRepository.findAdmitRequestDivisionByPatientId(patientId)
    }

    override fun removePatientFromRequestList(divisionId: String, patientId: String): Boolean {
        val div: MedicalDivision? = medicalDivisionRepository.find(divisionId);

        if(div != null) {
            div.removePatientFromRequestlist(patientId)
            medicalDivisionRepository.save(div)
            return true
        }
        return false
    }

    override fun getChargeNurseForRequest(divisionId: String, patientId: String): String? {
        val div: MedicalDivision? = medicalDivisionRepository.find(divisionId)

        if(div != null) {
            val req: AdmitRequest? = div.findRequestByPatientId(patientId)
            if (req != null) {
                return req.requestingChargeNurseId
            }
        }
        return null
    }

    override fun dischargePatient(bedNum: Int, roomNum: Int): Boolean {
        val bed: Bed? = bedRepository.findByNumAndRoom(bedNum, roomNum)

        if(bed != null) {
            bed.dischargePatient()
            bedRepository.save(bed)
            val div: MedicalDivision? = medicalDivisionRepository.find(bed.identifier)
            if(div != null) {
            div.occupiedBeds = div.occupiedBeds - 1;
                if(div.numBeds > div.occupiedBeds){
                    div.updateDivsionStatus("incomplete");
                }
            }
            return true
        }
        return false
    }

    override fun isDivisionComplete(divisionId: String): Boolean? {
        val div: MedicalDivision? = medicalDivisionRepository.find(divisionId)

        if(div != null) {
            return div.isDivisionComplete()
        }
        return null
    }

    override fun addToRequestlist(
        divisionId: String,
        patientId: String,
        requestRationale: String,
        priority: Int,
        requestingChargeNurseId: String,
        localDoctorID: String
    ): Boolean {
        val div: MedicalDivision? = medicalDivisionRepository.find(divisionId)

        if(div != null) {
            div.addPatientToRequestlist(patientId, requestRationale, priority, requestingChargeNurseId, localDoctorID)
            medicalDivisionRepository.save(div)
            return true
        }
        return false
    }

    override fun admitPatient(bedNum: Int, roomNum: Int, divisionId: String): Boolean {
        val bed: Bed = bedRepository.findByNumAndRoom(bedNum, roomNum) ?: return false
        val division: MedicalDivision = medicalDivisionRepository.find(divisionId) ?: return false

        if(!division.bedIds.contains(bed.identifier)) {
            return false
        }

        if(bed.isAvailable == true){
            bed.admitPatient()
            bedRepository.save(bed)
            val div: MedicalDivision? = medicalDivisionRepository.find(bed.identifier)
            if(div != null) {
                div.occupiedBeds = div.occupiedBeds + 1;
                if(div.numBeds == div.occupiedBeds){
                    div.updateDivsionStatus("complete");
                }
            }
            return true
        }
        else {
            return false
        }
    }

    override fun getDivision(divisionId: String): MedicalDivision? {
        return medicalDivisionRepository.find(divisionId)
    }

    override fun getBeds(bedIds: Array<String>): Array<Bed> {
        return bedRepository.findAllByIds(bedIds)
    }

    override fun getBed(bedId: String): Bed? {
        return bedRepository.find(bedId)
    }

    override fun getDivisions(): Array<MedicalDivision> {
        return medicalDivisionRepository.getAll()
    }

    override fun getDivisionIdByChargeNurse(chargeNurseId: String): String? {
        val medDiv = medicalDivisionRepository.findByChargeNurseId(chargeNurseId) ?: return null
        return medDiv.identifier
    }

}