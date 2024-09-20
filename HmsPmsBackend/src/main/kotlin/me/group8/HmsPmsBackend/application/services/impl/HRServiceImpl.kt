package me.group8.HmsPmsBackend.application.services.impl

import me.group8.HmsPmsBackend.application.db.postgres.repository.HRTableRepository
import me.group8.HmsPmsBackend.application.services.HRService
import org.springframework.stereotype.Service

@Service
class HRServiceImpl(
    private val hrTableRepository: HRTableRepository
): HRService {
    override fun doesEmployeeExist(employeeId: String): Boolean {
        return hrTableRepository.existsById(employeeId);
    }
}