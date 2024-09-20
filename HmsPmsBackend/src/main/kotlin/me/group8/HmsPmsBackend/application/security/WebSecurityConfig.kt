package me.group8.HmsPmsBackend.application.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey


@Configuration
class WebSecurityConfig(
) {

    @Value("certs/private.pem")
    lateinit var privateKey: RSAPrivateKey

    @Value("certs/public.pem")
    lateinit var publicKey: RSAPublicKey

    @Bean
    fun authenticationManager(authenticationConfig: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfig.authenticationManager
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .cors(Customizer.withDefaults())
            .csrf(({ csrf -> csrf.disable() }))
            .oauth2ResourceServer {
                config: OAuth2ResourceServerConfigurer<HttpSecurity> -> config.jwt(Customizer.withDefaults())
            }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(HttpMethod.OPTIONS, "**").permitAll()
                    .requestMatchers("/login").permitAll()
                        .requestMatchers("/register").permitAll()
                    .anyRequest().authenticated()
            }
            .logout(Customizer.withDefaults())
            .build()
    }
}