package me.group8.HmsPmsBackend.teststubs.services

import me.group8.HmsPmsBackend.domain.staff.services.StaffNotificationService

class StaffNotificationServiceStub: StaffNotificationService() {
    val notificationsSent: MutableList<String> = ArrayList()

    override fun sendRegistrationNotification(message: String) {
        notificationsSent.add(message)
    }

    override fun sendRejectionNotification(message: String) {
        notificationsSent.add(message)
    }
}