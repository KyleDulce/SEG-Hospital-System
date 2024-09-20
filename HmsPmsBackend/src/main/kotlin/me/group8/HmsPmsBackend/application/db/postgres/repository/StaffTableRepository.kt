package me.group8.HmsPmsBackend.application.db.postgres.repository;

import me.group8.HmsPmsBackend.application.db.postgres.entities.StaffEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StaffTableRepository : JpaRepository<StaffEntity, String> {
}