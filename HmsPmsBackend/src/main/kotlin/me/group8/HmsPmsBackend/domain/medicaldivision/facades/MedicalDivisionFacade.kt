package me.group8.HmsPmsBackend.domain.medicaldivision.facades

import me.group8.HmsPmsBackend.domain.medicaldivision.entities.AdmitRequest
import me.group8.HmsPmsBackend.domain.medicaldivision.entities.Bed
import me.group8.HmsPmsBackend.domain.medicaldivision.entities.MedicalDivision

interface MedicalDivisionFacade {
    fun isPatientInRequestList(divisionId: String, patientId: String): Boolean
    fun isPatientInRequestList(patientId: String): String?
    fun removePatientFromRequestList(divisionId: String, patientId: String): Boolean
    fun getChargeNurseForRequest(divisionId: String, patientId: String): String?
    fun dischargePatient(bedNum: Int, roomNum: Int): Boolean
    fun isDivisionComplete(divisionId: String): Boolean?
    fun addToRequestlist(divisionId: String, patientId: String, requestRationale: String, priority: Int, requestingChargeNurseId: String, localDoctorID: String): Boolean
    fun admitPatient(bedNum: Int, roomNum: Int, divisionId: String): Boolean
    fun getDivision(divisionId: String): MedicalDivision?
    fun getBeds(bedIds: Array<String>): Array<Bed>
    fun getBed(bedId: String): Bed?
    fun getDivisions(): Array<MedicalDivision>
    fun getDivisionIdByChargeNurse(chargeNurseId: String): String?
}