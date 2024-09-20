package me.group8.HmsPmsBackend.teststubs.services

import me.group8.HmsPmsBackend.application.services.HRService

class HRServiceStub: HRService {
    val employees: MutableList<String> = ArrayList()

    override fun doesEmployeeExist(employeeId: String): Boolean {
        return employees.contains(employeeId)
    }

}