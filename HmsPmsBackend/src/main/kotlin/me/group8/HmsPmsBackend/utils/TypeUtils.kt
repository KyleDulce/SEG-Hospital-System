package me.group8.HmsPmsBackend.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

class TypeUtils {
    companion object {
        fun utilDateToSqlDate(utilDate: Date): java.sql.Date {
            return java.sql.Date(utilDate.time)
        }

        fun blankSqlDate(): java.sql.Date {
            return java.sql.Date(0)
        }

        fun parseDate(dateOfBirth: String): Date {
            try {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                return dateFormat.parse(dateOfBirth)
            } catch (e: ParseException) {
                e.printStackTrace()
                return Date()
            }
        }
    }


}