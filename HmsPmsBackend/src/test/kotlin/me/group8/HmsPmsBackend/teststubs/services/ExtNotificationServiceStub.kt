package me.group8.HmsPmsBackend.teststubs.services

import me.group8.HmsPmsBackend.application.services.ExtNotificationService

class ExtNotificationServiceStub: ExtNotificationService {
    val notificationsSent: MutableList<String> = ArrayList()

    override fun sendDischargeNotification(patientId: String, extDoctorId: String) {
        notificationsSent.add(patientId + " " + extDoctorId)
    }
}