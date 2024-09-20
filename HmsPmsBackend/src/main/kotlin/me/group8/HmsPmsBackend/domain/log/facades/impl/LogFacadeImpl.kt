package me.group8.HmsPmsBackend.domain.log.facades.impl

import me.group8.HmsPmsBackend.domain.log.facades.LogFacade
import me.group8.HmsPmsBackend.domain.log.factories.LogFactory
import me.group8.HmsPmsBackend.domain.log.repositories.LogRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class LogFacadeImpl(
    val logFactory: LogFactory,
    val logRepository: LogRepository
): LogFacade {
    override fun logAccess(time: Date, staffId: String, patientId: String) {
        val log = logFactory.create(time, staffId, patientId)
        logRepository.save(log)
    }
}