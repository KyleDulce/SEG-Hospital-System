package me.group8.HmsPmsBackend.domain.patient.facades.impl

import me.group8.HmsPmsBackend.application.dtos.queries.InfectionDto
import me.group8.HmsPmsBackend.application.dtos.queries.PatientCreateDto
import me.group8.HmsPmsBackend.application.services.ExtNotificationService
import me.group8.HmsPmsBackend.domain.patient.entities.AdmissionRecord
import me.group8.HmsPmsBackend.domain.patient.entities.Infection
import me.group8.HmsPmsBackend.domain.patient.facades.PatientFacade
import me.group8.HmsPmsBackend.domain.patient.factories.AdmissionRecordFactory
import me.group8.HmsPmsBackend.domain.patient.factories.PatientFactory
import me.group8.HmsPmsBackend.domain.patient.repositories.AdmissionRecordRepository
import me.group8.HmsPmsBackend.domain.patient.repositories.PatientRepository
import me.group8.HmsPmsBackend.domain.patient.entities.Patient
import me.group8.HmsPmsBackend.domain.patient.factories.InfectionFactory
import me.group8.HmsPmsBackend.domain.patient.repositories.InfectionRepository
import org.springframework.stereotype.Service


@Service
class PatientFacadeImpl(
    val patientFactory: PatientFactory,
    val patientRepository: PatientRepository,
    val admissionRecordFactory: AdmissionRecordFactory,
    val admissionRecordRepository: AdmissionRecordRepository,
    val infectionRepository: InfectionRepository,
    val infectionFactory: InfectionFactory,
    val extNotificationService: ExtNotificationService
): PatientFacade {

    override fun registerPatient(patientInfo: PatientCreateDto): Boolean {
        val patientId = patientFactory.getNewPatientId()
        val patient = patientFactory.createNewPatient(patientInfo, patientId)
        patientRepository.save(patient)
        return true
    }

    override fun getCurrentPatientAdmission(patientId: String): AdmissionRecord? {
        return admissionRecordRepository.findByPatientId(patientId)
    }

    override fun dischargePatientAndNotify(patientId: String): Boolean {
        val admitRecord = admissionRecordRepository.findByPatientId(patientId)
        if(admitRecord == null) {
            return false
        }

        admitRecord.markDischarged()
        admissionRecordRepository.save(admitRecord)

        val patient = patientRepository.find(patientId)
        if(patient == null) {
            return false
        }

        extNotificationService.sendDischargeNotification(patientId, patient.extDoctorId)
        return true
    }

    override fun admitPatient(
        divisionId: String,
        roomNum: Int,
        bedNum: Int,
        patientId: String,
        privInsuranceNum: Long,
        localDoctorId: String
    ): Boolean {
        val admissionRecordInst = admissionRecordFactory.create(
            divisionId,
            roomNum,
            bedNum,
            patientId,
            privInsuranceNum,
            localDoctorId)
        admissionRecordRepository.save(admissionRecordInst)
        return true
    }

    override fun updatePatient(patientId: String, updateInfo: PatientCreateDto) {
        val patient = patientRepository.find(patientId)
        if(patient == null) {
            return
        }

        patient.updateInfo(updateInfo)
        patientRepository.save(patient)
    }

    override fun isPatientInCareOfDoctor(patientId: String, requestingDoctorId: String): Boolean {
        val admitRecord = admissionRecordRepository.findByPatientId(patientId)

        if(admitRecord != null) {
            return admitRecord.localDoctor.equals(requestingDoctorId)
        }
        return false
    }

    override fun isPatientInDivision(patientId: String): Boolean {
        val admissions = admissionRecordRepository.findAllByPatientId(patientId)

        if(admissions == null) {
            return false
        }

        for(admissionRecord : AdmissionRecord in admissions) {
            if(!admissionRecord.isDischarged) {
                return true
            }
        }
        return false
    }

    override fun getPatientInfo(patientId: String): Patient? {
        return patientRepository.find(patientId)
    }

    override fun getAllPatientAdmission(patientId: String): Array<AdmissionRecord> {
        return admissionRecordRepository.findAllByPatientId(patientId)
    }

    override fun getAllPatientInfections(patientId: String): Array<Infection> {
        return infectionRepository.findAllByPatientId(patientId)
    }

    override fun addInfection(infectionInfo: InfectionDto): Boolean {
        val infectionInst = infectionFactory.createInfection(infectionInfo)
        infectionRepository.save(infectionInst)
        return true
    }
    override fun updateInfection(infectionId: String, infectionInfo: InfectionDto) {
        val infection = infectionRepository.find(infectionId)
        if(infection == null) {
            return
        }

        infection.updateInfo(infectionInfo)
        infectionRepository.save(infection)

    }

}