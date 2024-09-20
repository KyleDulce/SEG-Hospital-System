package me.group8.HmsPmsBackend.utils

enum class StaffType(
    private val typeStr: String
) {
    DOCTOR("Doctor"),
    CHARGE_NURSE("ChargeNurse"),
    GENERIC_STAFF("Staff");

    override fun toString(): String {
        return typeStr
    }
}