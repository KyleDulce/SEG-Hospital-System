package me.group8.HmsPmsBackend.domain.patient.entities

class AdmissionRecord(
    val recordId: String,
    val patientId: String,
    val localDoctor: String,
    val divisionId: String,
    val roomNum: Int,
    val bedNumber: Int,
    val privInsuranceNum: Long?,
    var isDischarged: Boolean = false
) {

    fun markDischarged() {
        isDischarged = true
    }
}