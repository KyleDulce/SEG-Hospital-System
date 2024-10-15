package me.group8.HmsPmsBackend.application.controller.payload

import me.group8.HmsPmsBackend.domain.patient.entities.Patient
import me.group8.HmsPmsBackend.domain.patient.entities.AdmissionRecord
import me.group8.HmsPmsBackend.domain.medication.entities.Medication
import me.group8.HmsPmsBackend.domain.patient.entities.Infection

data class ConsultPatientFileResponse(
    val patientInfo: Patient,
    val admissionRecords: Array<AdmissionRecord>,
    val prescriptions: Array<Medication>,
    val infections: Array<Infection>
)