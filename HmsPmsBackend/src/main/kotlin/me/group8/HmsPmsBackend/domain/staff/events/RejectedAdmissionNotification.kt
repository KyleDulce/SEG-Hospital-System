package me.group8.HmsPmsBackend.domain.staff.events


class RejectedAdmissionNotification {
    fun send(message: String) {
        println("type: asmission rejection; message: " + message)
    }
}
