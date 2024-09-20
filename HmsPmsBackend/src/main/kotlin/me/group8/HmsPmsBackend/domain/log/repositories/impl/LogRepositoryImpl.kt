package me.group8.HmsPmsBackend.domain.log.repositories.impl

import me.group8.HmsPmsBackend.application.db.postgres.entities.LogEntity
import me.group8.HmsPmsBackend.application.db.postgres.repository.LogTableRepository
import me.group8.HmsPmsBackend.domain.log.entities.Log
import me.group8.HmsPmsBackend.domain.log.repositories.LogRepository
import org.springframework.stereotype.Service
import java.sql.Timestamp

@Service
class LogRepositoryImpl(
    private val logTableRepository: LogTableRepository
) : LogRepository {
    override fun save(log: Log): Log {
        logTableRepository.save(toLogEntity(log))
        return log
    }

    private fun toLogEntity(log: Log): LogEntity {
        return LogEntity(
            0,
            Timestamp(log.time.time),
            log.staffId,
            log.patientId
        )
    }
}