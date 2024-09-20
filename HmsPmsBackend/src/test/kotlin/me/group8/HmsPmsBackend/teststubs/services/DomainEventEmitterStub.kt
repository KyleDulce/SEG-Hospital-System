package me.group8.HmsPmsBackend.teststubs.services

import me.group8.HmsPmsBackend.application.services.DomainEventEmitter
import me.group8.HmsPmsBackend.domain.DomainEvent

class DomainEventEmitterStub: DomainEventEmitter {
    val eventsEmited: MutableList<DomainEvent> = ArrayList()
    override fun emit(event: DomainEvent) {
        eventsEmited.add(event)
    }
}