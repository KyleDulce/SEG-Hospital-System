package me.group8.HmsPmsBackend.application.services.impl

import me.group8.HmsPmsBackend.application.services.DomainEventEmitter
import me.group8.HmsPmsBackend.domain.DomainEvent
import org.springframework.stereotype.Service

@Service
class DomainEventEmitterImpl: DomainEventEmitter {
    override fun emit(event: DomainEvent) {
        //nothing
    }
}