package me.group8.HmsPmsBackend.teststubs.repositories

import me.group8.HmsPmsBackend.domain.medication.entities.Medication
import me.group8.HmsPmsBackend.domain.medication.repositories.MedicationRepository

class MedicationRepositoryStub: MedicationRepository {
    val meds: MutableList<Medication> = ArrayList()

    override fun save(medication: Medication): Medication {
        meds.add(medication)
        return medication
    }
}