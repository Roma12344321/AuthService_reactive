package com.martynov.spring.repositories

import com.martynov.spring.models.Person
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface PersonRepository : ReactiveCrudRepository<Person, Int> {
    fun findByUsername(username : String) : Mono<Person>
}