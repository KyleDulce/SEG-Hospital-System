package me.group8.HmsPmsBackend.application.security

import me.group8.HmsPmsBackend.application.usecases.StaffLogIn
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class HmsUserDetailsService(
    val staffLogIn: StaffLogIn
): UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        if(username == null) {
            throw UsernameNotFoundException("null username!")
        }
        val staff = staffLogIn.getStaffObj(username) ?: throw UsernameNotFoundException(username)
        return UserPrincipleDetails(staff)
    }
}