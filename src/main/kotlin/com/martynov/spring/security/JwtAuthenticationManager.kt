package com.martynov.spring.security

import com.auth0.jwt.exceptions.JWTVerificationException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class JwtAuthenticationManager @Autowired constructor(
    private val jwtUtil: JWTUtil
) : ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        val authToken = authentication.credentials.toString()

        return try {
            val username = jwtUtil.validateToken(authToken)
            val authorities = extractAuthorities()
            Mono.just(UsernamePasswordAuthenticationToken(username, null, authorities))
        } catch (ex: JWTVerificationException) {
            Mono.error(ex)
        }
    }

    private fun extractAuthorities(): List<GrantedAuthority> {
        val roles = Role.roles
        return roles.map { SimpleGrantedAuthority(it) }
    }


}