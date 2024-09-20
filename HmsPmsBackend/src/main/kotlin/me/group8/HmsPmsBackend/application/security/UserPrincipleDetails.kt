package me.group8.HmsPmsBackend.application.security

import me.group8.HmsPmsBackend.domain.staff.entities.GenericStaff
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.Collections

class UserPrincipleDetails(
    private val user: GenericStaff
): UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority("Staff"))
    }

    override fun getPassword(): String {
        return "{noop}" + user.password
    }

    override fun getUsername(): String {
        return user.employeeId
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}