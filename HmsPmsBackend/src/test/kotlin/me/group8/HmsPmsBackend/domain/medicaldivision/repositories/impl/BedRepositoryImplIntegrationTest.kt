package me.group8.HmsPmsBackend.domain.medicaldivision.repositories.impl

import me.group8.HmsPmsBackend.application.db.postgres.entities.BedEntity
import me.group8.HmsPmsBackend.application.db.postgres.repository.BedTableRepository
import me.group8.HmsPmsBackend.domain.medicaldivision.entities.Bed
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled

@SpringBootTest
@Transactional
class BedRepositoryImplIntegrationTest {
    @Autowired
    lateinit var bedRepositoryImpl: BedRepositoryImpl
    @Autowired
    lateinit var bedTableRepository: BedTableRepository

    @Test
    fun testFind_success() {
        val expectedBed = BedEntity(
            "fe2ca979-1683-404e-94af-e131a771a11f",
            1,
            1,
            true
        )
        bedTableRepository.save(expectedBed)

        val actual = bedRepositoryImpl.find(expectedBed.identifier)

        assertNotNull(actual)
        assertBedEntityEqualsBed(expectedBed, actual!!)
    }

    @Test
    fun testFind_notFound_nullResult() {
        val actual = bedRepositoryImpl.find("779f7829-8674-4eb0-bf76-0512ed74cbbd")

        assertNull(actual)
    }

    @Test
    fun testFindByNumAndRoom_success() {
        val expectedBed = BedEntity(
            "fe2ca979-1683-404e-94af-e131a771a11f",
            -100,
            -100,
            true
        )
        bedTableRepository.save(expectedBed)

        val actual = bedRepositoryImpl.findByNumAndRoom(expectedBed.roomNum, expectedBed.bedNum)

        assertNotNull(actual)
        assertBedEntityEqualsBed(expectedBed, actual!!)
    }

    @Test
    fun testFindByNumAndRoom_notFound_nullResult() {
        val actual = bedRepositoryImpl.findByNumAndRoom(-404, -404)

        assertNull(actual)
    }

    @Test
    fun testSave_success() {
        val expectedBed = Bed(
            "191d9aaf-b37c-47a4-a64b-7f9947051355",
            2,
            2,
            false
        )

        bedRepositoryImpl.save(expectedBed)

        val actual = bedTableRepository.findById(expectedBed.identifier)

        assertTrue(actual.isPresent)
        assertBedEqualsBedEntity(expectedBed, actual.get())
    }

    fun assertBedEntityEqualsBed(bedEntity: BedEntity, bed: Bed) {
        assertEquals(bedEntity.identifier, bed.identifier)
        assertEquals(bedEntity.roomNum, bed.roomNum)
        assertEquals(bedEntity.bedNum, bed.bedNum)
        assertEquals(bedEntity.isAvailable, bed.isAvailable)
    }

    fun assertBedEqualsBedEntity(bed: Bed, bedEntity: BedEntity) {
        assertEquals(bed.identifier, bedEntity.identifier)
        assertEquals( bed.roomNum, bedEntity.roomNum)
        assertEquals(bed.bedNum, bedEntity.bedNum)
        assertEquals(bed.isAvailable, bedEntity.isAvailable)
    }
}