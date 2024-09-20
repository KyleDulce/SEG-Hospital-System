package me.group8.HmsPmsBackend.domain.log.factories

import me.group8.HmsPmsBackend.domain.log.entities.Log
import org.springframework.stereotype.Service
import java.util.*

@Service
class LogFactory {
    fun create(time: Date, staffId: String, patientId: String): Log {
        return Log(
            time,
            staffId,
            patientId
        )
    }
}