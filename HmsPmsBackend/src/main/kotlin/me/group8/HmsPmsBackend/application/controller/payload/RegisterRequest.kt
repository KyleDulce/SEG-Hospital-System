package me.group8.HmsPmsBackend.application.controller.payload

data class RegisterRequest (
        val firstName: String,
        val lastName: String,
        val dateOfBirth: String,
        val phoneNumber: Long,
        val email: String,
        val streetNumber: Int,
        val unit: Int,
        val streetName: String,
        val zipCode: String,
        val city :String,
        val country: String,
        val province:String,
        val staffId: String,
        val password: String,
        val retypedPass: String,
        val teleExt: Number,
        val modPerm: Boolean,
        val staffType: String,
        val bipperExtension: Int?,
        val divName: String?
        )
