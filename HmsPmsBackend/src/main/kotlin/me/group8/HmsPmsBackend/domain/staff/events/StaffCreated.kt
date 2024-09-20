package me.group8.HmsPmsBackend.domain.staff.events

import me.group8.HmsPmsBackend.domain.DomainEvent
import java.util.*

class StaffCreated (val id: String,
                    val employeeId: String): DomainEvent
