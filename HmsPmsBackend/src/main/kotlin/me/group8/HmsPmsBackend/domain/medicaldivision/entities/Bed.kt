package me.group8.HmsPmsBackend.domain.medicaldivision.entities

class Bed(
    val identifier: String,
    val roomNum: Int,
    val bedNum: Int,
    var isAvailable: Boolean = true
) {
    fun dischargePatient() {
        isAvailable = true
    }
    fun admitPatient() {
        isAvailable = false
    }
}