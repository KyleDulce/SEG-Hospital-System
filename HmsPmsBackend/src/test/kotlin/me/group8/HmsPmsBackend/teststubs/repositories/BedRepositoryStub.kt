package me.group8.HmsPmsBackend.teststubs.repositories

import me.group8.HmsPmsBackend.domain.medicaldivision.entities.Bed
import me.group8.HmsPmsBackend.domain.medicaldivision.repositories.BedRepository

class BedRepositoryStub: BedRepository {
    val bedMap: MutableMap<String, Bed> = HashMap()

    override fun find(bedId: String): Bed? {
        return bedMap[bedId]
    }

    override fun findByNumAndRoom(bedNum: Int, roomId: Int): Bed? {
        for (bed : Bed in bedMap.values) {
            if(bed.bedNum == bedNum && bed.roomNum == roomId) {
                return bed
            }
        }
        return null
    }

    override fun findAllByIds(bedId: Array<String>): Array<Bed> {
        return bedId.map {
            bId -> bedMap[bId]
        }
            .filterNotNull()
            .toTypedArray()
    }

    override fun save(bed: Bed): Bed {
        bedMap[bed.identifier] = bed
        return bed
    }
}