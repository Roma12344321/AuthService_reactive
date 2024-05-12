package com.martynov.spring.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class JwtSecurityContextRepository @Autowired constructor(
    private val authenticationManager : JwtAuthenticationManager
) : ServerSecurityContextRepository {

    override fun load(exchange: ServerWebExchange): Mono<SecurityContext> {
        val token = exchange.request.headers.getFirst("Authorization")?.substringAfter("Bearer ")
        return if (token != null) {
            val auth = UsernamePasswordAuthenticationToken(token, token)
            authenticationManager.authenticate(auth).map { authentication ->
                SecurityContextImpl(authentication)
            }
        } else {
            Mono.empty()
        }
    }

    override fun save(exchange: ServerWebExchange, context: SecurityContext): Mono<Void> {
        throw UnsupportedOperationException("Save method not supported")
    }
}