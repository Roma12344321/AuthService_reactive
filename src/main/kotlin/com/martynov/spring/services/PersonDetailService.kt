package com.martynov.spring.services

import com.martynov.spring.security.PersonDetails
import com.martynov.spring.repositories.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class PersonDetailService @Autowired constructor(
    private val personRepository: PersonRepository
) : ReactiveUserDetailsService {

    override fun findByUsername(username: String?): Mono<UserDetails> {
        if (username == null) throw RuntimeException("username is null")
        return personRepository.findByUsername(username).map {
            PersonDetails(it)
        }
    }
}