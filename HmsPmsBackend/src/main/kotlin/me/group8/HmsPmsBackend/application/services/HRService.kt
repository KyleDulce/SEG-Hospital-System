package me.group8.HmsPmsBackend.application.services

interface HRService {
    fun doesEmployeeExist(employeeId: String): Boolean
}
