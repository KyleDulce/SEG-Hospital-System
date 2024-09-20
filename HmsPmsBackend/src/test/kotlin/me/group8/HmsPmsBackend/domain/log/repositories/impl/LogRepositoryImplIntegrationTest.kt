package me.group8.HmsPmsBackend.domain.log.repositories.impl

import me.group8.HmsPmsBackend.application.db.postgres.entities.LogEntity
import me.group8.HmsPmsBackend.application.db.postgres.entities.id.LogEntityId
import me.group8.HmsPmsBackend.application.db.postgres.repository.LogTableRepository
import me.group8.HmsPmsBackend.domain.log.entities.Log
import me.group8.HmsPmsBackend.utils.DbTestUtils
import me.group8.HmsPmsBackend.utils.TypeUtils
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled

@SpringBootTest
@Transactional
class LogRepositoryImplIntegrationTest {

    @Autowired
    lateinit var logRepositoryImpl: LogRepositoryImpl
    @Autowired
    lateinit var logTableRepository: LogTableRepository

    @Test
    fun testSave_success() {
        val log = Log(
            DbTestUtils.generateDateUtils(),
            "someId",
            DbTestUtils.generateUUID()
        )

        logRepositoryImpl.save(log)
        val actual = logTableRepository.findById(
            LogEntityId(TypeUtils.utilDateToSqlDate(log.time), log.staffId, log.patientId)
        )

        assertTrue(actual.isPresent)
        assertLogEqualsLogEntity(log, actual.get())
    }

    fun assertLogEqualsLogEntity(log: Log, logEntity: LogEntity) {
        assertEquals(log.time.time, logEntity.logEntityId.time.time)
        assertEquals(log.staffId, logEntity.logEntityId.staffId)
        assertEquals(log.patientId, logEntity.logEntityId.patientId)
    }
}