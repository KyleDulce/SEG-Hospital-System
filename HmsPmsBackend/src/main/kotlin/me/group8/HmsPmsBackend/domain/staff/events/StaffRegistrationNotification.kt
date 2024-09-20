package me.group8.HmsPmsBackend.domain.staff.events

class StaffRegistrationNotification {
    fun send(message: String) {
        println("type: staff registration; message: " + message)
    }
}
