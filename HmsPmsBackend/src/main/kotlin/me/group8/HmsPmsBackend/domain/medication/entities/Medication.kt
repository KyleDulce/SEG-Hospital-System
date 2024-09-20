package me.group8.HmsPmsBackend.domain.medication.entities


import java.util.Date

class Medication (
    val patientId: String,
    val identifier: Long,
    val name: String,
    val unitByDay: Int,
    val adminByDay: Int,
    val methodOfAdministration: String,
    val startDate: Date,
    val endDate: Date,
) {
    val medAdmins: MutableList<MedicationAdministration> = ArrayList()
}