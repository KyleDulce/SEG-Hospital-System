package me.group8.HmsPmsBackend.domain.staff.services

import me.group8.HmsPmsBackend.domain.staff.events.RejectedAdmissionNotification
import me.group8.HmsPmsBackend.domain.staff.events.StaffRegistrationNotification
import org.springframework.stereotype.Service

@Service
class StaffNotificationService {
    fun sendRegistrationNotification(message: String) {
        val notification = StaffRegistrationNotification()
        notification.send(message)
    }

    fun sendRejectionNotification(message: String) {
        val notification = RejectedAdmissionNotification()
        notification.send(message)
    }
}