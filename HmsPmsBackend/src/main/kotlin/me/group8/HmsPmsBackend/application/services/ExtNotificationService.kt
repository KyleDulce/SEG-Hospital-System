package me.group8.HmsPmsBackend.application.services


interface ExtNotificationService {
    fun sendDischargeNotification(patientId: String, extDoctorId: String)
}