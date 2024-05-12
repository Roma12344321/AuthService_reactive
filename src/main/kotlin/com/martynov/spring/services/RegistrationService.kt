package com.martynov.spring.services

import com.martynov.spring.models.Person
import com.martynov.spring.repositories.PersonRepository
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegistrationService @Autowired constructor(
    private val passwordEncoder: PasswordEncoder,
    private val personRepository: PersonRepository
) {
    @Transactional
    suspend fun register(person: Person) {
        val encodedPassword: String = passwordEncoder.encode(person.password)
        person.role = "ROLE_USER"
        person.password = encodedPassword
        personRepository.save(person).awaitSingle()
    }
}