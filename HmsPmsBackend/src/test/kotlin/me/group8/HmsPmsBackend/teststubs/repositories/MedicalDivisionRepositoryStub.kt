package me.group8.HmsPmsBackend.teststubs.repositories

import me.group8.HmsPmsBackend.domain.medicaldivision.entities.MedicalDivision
import me.group8.HmsPmsBackend.domain.medicaldivision.repositories.MedicalDivisionRepository

class MedicalDivisionRepositoryStub: MedicalDivisionRepository {
    val divMap: MutableMap<String, MedicalDivision> = HashMap()

    override fun find(divisionId: String): MedicalDivision? {
        return divMap[divisionId]
    }

    override fun save(divisionId: MedicalDivision): MedicalDivision {
        divMap[divisionId.identifier] = divisionId
        return divisionId
    }

    override fun getAll(): Array<MedicalDivision> {
        return divMap.values.toTypedArray()
    }
}