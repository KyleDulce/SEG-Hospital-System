package me.group8.HmsPmsBackend.application.db.postgres.repository

import me.group8.HmsPmsBackend.application.db.postgres.entities.BedEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface BedTableRepository : JpaRepository<BedEntity, String> {
    fun findByRoomNumAndBedNum(roomNum: Int, bedNum: Int): Optional<BedEntity>
}