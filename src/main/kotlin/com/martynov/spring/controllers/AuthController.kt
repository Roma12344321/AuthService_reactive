package com.martynov.spring.controllers

import com.auth0.jwt.exceptions.JWTVerificationException
import com.martynov.spring.security.JWTUtil
import com.martynov.spring.security.JwtAuthenticationManager
import com.martynov.spring.dto.AuthenticationDto
import com.martynov.spring.models.Person
import com.martynov.spring.services.RegistrationService
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController @Autowired constructor(
    private val registrationService: RegistrationService,
    private val jwtUtil: JWTUtil,
    private val authenticationManager: JwtAuthenticationManager
) {


    @PostMapping("/registration")
    suspend fun performRegistration(
        @RequestBody person: Person?,
    ): Map<String, String> {
        registrationService.register(person ?: throw RuntimeException())
        val token = jwtUtil.generateToken(person.username)
        return java.util.Map.of("jwt_token", token)
    }

    @PostMapping("/login")
    suspend fun performLogin(@RequestBody authenticationDto: AuthenticationDto): Map<String, String> {
        val authInputToken = UsernamePasswordAuthenticationToken(
            authenticationDto.username, authenticationDto.password
        )
        try {
            authenticationManager.authenticate(authInputToken)
        } catch (e: BadCredentialsException) {
            return java.util.Map.of("message", "incorrect credentials")
        }
        val token: String = jwtUtil.generateToken(authenticationDto.username)
        return java.util.Map.of("jwt_token", token)
    }
}