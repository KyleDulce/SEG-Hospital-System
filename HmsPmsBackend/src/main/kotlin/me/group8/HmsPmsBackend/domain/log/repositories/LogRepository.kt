package me.group8.HmsPmsBackend.domain.log.repositories

import me.group8.HmsPmsBackend.domain.log.entities.Log

interface LogRepository {
    fun save(log: Log): Log
}