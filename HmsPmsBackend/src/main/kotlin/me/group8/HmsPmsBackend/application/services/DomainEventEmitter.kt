package me.group8.HmsPmsBackend.application.services

import me.group8.HmsPmsBackend.domain.DomainEvent

interface DomainEventEmitter {
    fun emit(event: DomainEvent)
}