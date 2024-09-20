package me.group8.HmsPmsBackend.application.services.impl

import me.group8.HmsPmsBackend.application.notification.Notification
import me.group8.HmsPmsBackend.application.services.ExtNotificationService
import org.springframework.stereotype.Service

@Service
class ExtNotificationServiceImpl: ExtNotificationService{
     override fun sendDischargeNotification(patientId: String, extDoctorId: String) {
        val notification = Notification()
        notification.send(patientId, extDoctorId, "discharge")
    }


}