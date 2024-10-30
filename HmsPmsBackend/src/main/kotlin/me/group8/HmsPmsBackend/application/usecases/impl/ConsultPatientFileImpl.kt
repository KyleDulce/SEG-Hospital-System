package me.group8.HmsPmsBackend.application.usecases.impl

import me.group8.HmsPmsBackend.application.controller.payload.LocationResponse
import me.group8.HmsPmsBackend.application.usecases.ConsultPatientFile
import me.group8.HmsPmsBackend.domain.log.facades.LogFacade
import me.group8.HmsPmsBackend.domain.patient.facades.PatientFacade
import me.group8.HmsPmsBackend.domain.medication.facades.MedicationFacade
import me.group8.HmsPmsBackend.domain.medication.entities.Medication
import me.group8.HmsPmsBackend.domain.patient.entities.*
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date

@Service
class ConsultPatientFileImpl(
    val patientFacade: PatientFacade, 
    val medicationFacade: MedicationFacade,
    val logFacade: LogFacade
): ConsultPatientFile {

    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

    override fun getPatientFile(patientId: String): Patient? {
        return patientFacade.getPatientInfo(patientId)
        
    }

    override fun getAllPatientAdmission(patientId: String): Array<AdmissionRecord> {
        return patientFacade.getAllPatientAdmission(patientId)
    }

    override fun getAllPatientPrescriptions(patientId: String): Array<Medication> {
        return medicationFacade.getAllPatientPrescriptions(patientId)
    }

    override fun getAllPatientInfections(patientId: String): Array<Infection> {
        return patientFacade.getAllPatientInfections(patientId)
    }

    override fun getPatientInfectionStatus(patientId: String): InfectionStatus {
        return patientFacade.getPatientInfectionStatus(patientId)
    }

    override fun getAllPatientLocations(patientId: String): Array<LocationResponse?> {
        val locations = patientFacade.getAllPatientLocations(patientId)

        return locations.mapNotNull(fun(loc: Location?): LocationResponse? {
            val tracking = loc?.let { patientFacade.getLocationTrackingFromLocationId(it.locationId) }?.get(0)
            if (tracking != null) {
                return LocationResponse(
                    loc,
                    simpleDateFormat.format(tracking.startDate),
                    simpleDateFormat.format(tracking.endDate)
                )
            }
            return null
        }).toTypedArray()
    }

    override fun logAccess(employeeId: String, patientId: String) {
        val currentDate = Date()
        logFacade.logAccess(currentDate, employeeId, patientId)
    }

}