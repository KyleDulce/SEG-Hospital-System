package me.group8.HmsPmsBackend.application.notification

class Notification {
    fun send(field: String, type: String) {
        println("type: $type; field: $field;")
    }
    fun send(field1: String, field2: String, type: String) {
        println("type: $type; field1: $field1; field2: $field2;")
    }
}