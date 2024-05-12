package com.martynov.spring.util

import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebExceptionHandler
import reactor.core.publisher.Mono

@Component
@Order(-2)
class GlobalWebExceptionHandler : WebExceptionHandler {
    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        if (ex is BadCredentialsException) {
            exchange.response.statusCode = HttpStatus.UNAUTHORIZED
            val bytes = "Invalid credentials".toByteArray()
            val buffer = exchange.response.bufferFactory().wrap(bytes)
            return exchange.response.writeWith(Mono.just(buffer))
        }
        if (ex is TokenHasExpiredException) {
            exchange.response.statusCode = HttpStatus.UNAUTHORIZED
            val bytes = "Invalid credentials".toByteArray()
            val buffer = exchange.response.bufferFactory().wrap(bytes)
            return exchange.response.writeWith(Mono.just(buffer))
        }
        return Mono.error(ex)
    }
}
