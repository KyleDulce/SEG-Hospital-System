package me.group8.HmsPmsBackend.domain.patient.facades.impl

import me.group8.HmsPmsBackend.application.dtos.queries.AddressCreateDto
import me.group8.HmsPmsBackend.application.dtos.queries.InfectionDto
import me.group8.HmsPmsBackend.application.dtos.queries.PatientCreateDto
import me.group8.HmsPmsBackend.application.services.ExtNotificationService
import me.group8.HmsPmsBackend.domain.patient.entities.*
import me.group8.HmsPmsBackend.domain.patient.facades.PatientFacade
import me.group8.HmsPmsBackend.domain.patient.factories.AdmissionRecordFactory
import me.group8.HmsPmsBackend.domain.patient.factories.PatientFactory
import me.group8.HmsPmsBackend.domain.patient.repositories.AdmissionRecordRepository
import me.group8.HmsPmsBackend.domain.patient.repositories.PatientRepository
import me.group8.HmsPmsBackend.domain.patient.factories.InfectionFactory
import me.group8.HmsPmsBackend.domain.patient.factories.LocationFactory
import me.group8.HmsPmsBackend.domain.patient.repositories.InfectionRepository
import me.group8.HmsPmsBackend.domain.patient.repositories.PatientLocationRepository
import org.springframework.stereotype.Service
import java.util.Date


@Service
class PatientFacadeImpl(
        val patientFactory: PatientFactory,
        val patientRepository: PatientRepository,
        val admissionRecordFactory: AdmissionRecordFactory,
        val admissionRecordRepository: AdmissionRecordRepository,
        val infectionRepository: InfectionRepository,
        val infectionFactory: InfectionFactory,
        val locationRepository: PatientLocationRepository,
        val locationFactory: LocationFactory,
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

    private fun getPatientIsInfected(patientId: String): Boolean {
        val infections = infectionRepository.findAllByPatientId(patientId)
        val currentDate = Date() // Get the current date

        for (data in infections) {
            // Check if the end date is null or if it is after today
            if (data.endDate == null || data.endDate.after(currentDate)) {
                return true
            }
        }

        return false
    }

    override fun getPatientInfectionStatus(patientId: String): InfectionStatus {
        // check if patient is infected
        if (getPatientIsInfected(patientId)) {
            return InfectionStatus.INFECTED
        }

        val locations = locationRepository.findAllByPatientId(patientId)
        val patientSameLoc = mutableSetOf<String>()

        // get all the patients that were in the same location
        for (loc in locations) {
            val patients = locationRepository.findPatientIdsByLocationId(loc?.locationId)
            for (p in patients) {
                patientSameLoc.add(p)
            }
        }

        // check if any of the patients in the same location are infected
        for (pId in patientSameLoc) {
            if (getPatientIsInfected(pId)) {
                return InfectionStatus.MAY_BE_INFECTED
            }
        }

        return InfectionStatus.NOT_INFECTED
    }

    override fun getAllPatientLocations(patientId: String): Array<Location?> {
        return locationRepository.findAllByPatientId(patientId)
    }

    override fun addLocationTracking(locationId: String, patientId: String): Boolean {
        locationRepository.saveLocationTracking(locationId, patientId)
        return true
    }

    override fun addLocation(locationInfo: AddressCreateDto): String {
        val locationInst = locationFactory.createLocation(locationInfo)
        locationRepository.save(locationInst)
        return locationInst.locationId
    }

    override fun updateLocation(locationId: String, locationInfo: AddressCreateDto) {
        val location = locationRepository.find(locationId)
        if(location == null) {
            return
        }

        location.updateInfo(locationInfo)
        locationRepository.save(location)

    }

}