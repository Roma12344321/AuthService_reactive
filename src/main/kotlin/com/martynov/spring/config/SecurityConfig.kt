package com.martynov.spring.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain

@EnableWebFluxSecurity
@Configuration
class SecurityConfig {

    @Bean
    fun passwordEncoder() : PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityWebFilterChain(
        http: ServerHttpSecurity,
        jwtAuthenticationManager: JwtAuthenticationManager,
        jwtSecurityContextRepository: JwtSecurityContextRepository
    ): SecurityWebFilterChain {
        http
            .csrf { it.disable() }
            .authenticationManager(jwtAuthenticationManager)
            .securityContextRepository(jwtSecurityContextRepository)
            .authorizeExchange {
                it.pathMatchers("/auth/**").permitAll()
                it.pathMatchers("/hello").hasAnyRole("USER","ADMIN")
            }


        return http.build()
    }
}