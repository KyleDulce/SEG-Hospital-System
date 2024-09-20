package me.group8.HmsPmsBackend.domain.medicaldivision.repositories.impl

import me.group8.HmsPmsBackend.application.db.postgres.entities.BedEntity
import me.group8.HmsPmsBackend.application.db.postgres.repository.BedTableRepository
import me.group8.HmsPmsBackend.domain.medicaldivision.entities.Bed
import me.group8.HmsPmsBackend.domain.medicaldivision.repositories.BedRepository
import org.springframework.stereotype.Service

@Service
class BedRepositoryImpl(
    private val bedTableRepository: BedTableRepository
): BedRepository {
    override fun find(bedId: String): Bed? {
        val result = bedTableRepository.findById(bedId)
        if (result.isEmpty) {
            return null
        }

        return bedEntityToBed(result.get())
    }

    override fun findByNumAndRoom(bedNum: Int, roomNum: Int): Bed? {
        val result = bedTableRepository.findByRoomNumAndBedNum(roomNum, bedNum)
        if (result.isEmpty) {
            return null
        }

        return bedEntityToBed(result.get())
    }

    override fun findAllByIds(bedIds: Array<String>): Array<Bed> {
        return bedTableRepository.findAllById(bedIds.asList())
            .map { bed -> bedEntityToBed(bed) }
            .toTypedArray()
    }

    override fun save(bed: Bed): Bed {
        bedTableRepository.save(bedToBedEntity(bed))
        return bed
    }

    private fun bedEntityToBed(bedEntity: BedEntity): Bed {
        return Bed(
            bedEntity.identifier,
            bedEntity.roomNum,
            bedEntity.bedNum,
            bedEntity.isAvailable
        )
    }

    private fun bedToBedEntity(bed: Bed): BedEntity {
        return BedEntity(
            bed.identifier,
            bed.roomNum,
            bed.bedNum,
            bed.isAvailable
        )
    }
}