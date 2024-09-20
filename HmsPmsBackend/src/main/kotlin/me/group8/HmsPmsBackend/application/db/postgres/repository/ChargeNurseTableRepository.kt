package me.group8.HmsPmsBackend.application.db.postgres.repository;

import me.group8.HmsPmsBackend.application.db.postgres.entities.ChargeNurseEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ChargeNurseTableRepository : JpaRepository<ChargeNurseEntity, String> {
}