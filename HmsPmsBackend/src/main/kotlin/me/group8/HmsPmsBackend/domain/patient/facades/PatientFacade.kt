package me.group8.HmsPmsBackend.domain.patient.facades

import me.group8.HmsPmsBackend.application.dtos.queries.AddressCreateDto
import me.group8.HmsPmsBackend.application.dtos.queries.InfectionDto
import me.group8.HmsPmsBackend.application.dtos.queries.PatientCreateDto
import me.group8.HmsPmsBackend.domain.patient.entities.*

interface PatientFacade {
    fun registerPatient(patientInfo: PatientCreateDto): Boolean
    fun getCurrentPatientAdmission(patientId: String): AdmissionRecord?
    fun dischargePatientAndNotify(patientId: String): Boolean
    fun admitPatient(divisionId: String, roomNum: Int, bedNum: Int, patientId: String, privInsuranceNum: Long, localDoctorId: String): Boolean
    fun updatePatient(patientId: String, updateInfo: PatientCreateDto)
    fun isPatientInCareOfDoctor(patientId: String, requestingDoctorId: String): Boolean
    fun isPatientInDivision(patientId: String): Boolean
    fun getPatientInfo(patientId: String): Patient?
    fun getAllPatientAdmission(patientId: String): Array<AdmissionRecord>

    fun getAllPatientInfections(patientId: String): Array<Infection>
    fun addInfection(infectionInfo: InfectionDto): Boolean
    fun updateInfection(infectionId: String, infectionInfo: InfectionDto)
    fun getPatientInfectionStatus(patientId: String): InfectionStatus

    fun getAllPatientLocations(patientId: String): Array<Location?>
    fun addLocationTracking(locationId: String, patientId: String): Boolean
    fun addLocation(locationInfo: AddressCreateDto): String
    fun updateLocation(locationId: String, locationInfo: AddressCreateDto)

}