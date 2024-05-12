package com.martynov.spring.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import java.util.*

@Component
class JWTUtil {

    @Value("\${jwt_secret}")
    private val secret: String? = null

    fun generateToken(username: String?): String {
        val expirationDate = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant())

        return JWT.create()
            .withSubject("User details")
            .withClaim("username", username)
            .withIssuedAt(Date())
            .withIssuer("outsquare")
            .withExpiresAt(expirationDate)
            .sign(Algorithm.HMAC256(secret))
    }

    @Throws(JWTVerificationException::class)
    fun validateToken(token: String?): String {
        val verifier = JWT.require(Algorithm.HMAC256(secret))
            .withSubject("User details")
            .withIssuer("outsquare")
            .build()
        val jwt = verifier.verify(token)
        return jwt.getClaim("username").asString()
    }
}
