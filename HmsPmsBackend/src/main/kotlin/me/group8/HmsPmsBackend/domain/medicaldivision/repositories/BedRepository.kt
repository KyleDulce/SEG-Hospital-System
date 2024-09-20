package me.group8.HmsPmsBackend.domain.medicaldivision.repositories

import me.group8.HmsPmsBackend.domain.medicaldivision.entities.Bed

interface BedRepository {
    fun find(bedId: String): Bed?
    fun findByNumAndRoom(bedNum: Int, roomNum: Int): Bed?
    fun findAllByIds(bedId: Array<String>): Array<Bed>
    fun save(bed: Bed): Bed
}