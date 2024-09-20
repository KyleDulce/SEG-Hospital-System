package me.group8.HmsPmsBackend.utils

import java.util.*

class DbTestUtils {
    companion object {
        fun generateDateUtils(): Date {
            return Date(10000)
        }

        fun generateDateSql(): java.sql.Date {
            return java.sql.Date(10000)
        }

        fun generateUUID(): String {
            return UUID.randomUUID().toString()
        }
    }
}