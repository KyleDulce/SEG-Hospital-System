package me.group8.HmsPmsBackend.application.security.jwt

import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import me.group8.HmsPmsBackend.application.security.WebSecurityConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter

@Configuration
class JwtConfig(
    private val securityConfig: WebSecurityConfig
) {
    @Bean
    fun jwtEncoder(): JwtEncoder {
        val jwk: JWK = RSAKey
            .Builder(securityConfig.publicKey)
            .privateKey(securityConfig.privateKey)
            .build()
        val jwkSource: JWKSource<SecurityContext> = ImmutableJWKSet<SecurityContext>(JWKSet(jwk))
        return NimbusJwtEncoder(jwkSource)
    }

    @Bean
    fun jwtDecoder(): JwtDecoder {
        return NimbusJwtDecoder
            .withPublicKey(securityConfig.publicKey)
            .build()
    }

    @Bean
    fun jwtAuthenticationConverter(): JwtAuthenticationConverter {
        val grantedAuthConverter = JwtGrantedAuthoritiesConverter()
        grantedAuthConverter.setAuthorityPrefix("")

        val jwtAuthConverter = JwtAuthenticationConverter()
        jwtAuthConverter.setJwtGrantedAuthoritiesConverter(grantedAuthConverter)
        return jwtAuthConverter
    }
}