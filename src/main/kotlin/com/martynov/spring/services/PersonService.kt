package com.martynov.spring.services

import com.martynov.spring.security.PersonDetails
import com.martynov.spring.models.Person
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Service

@Service
class PersonService {

    suspend fun getCurrentUser(): Person? {
        return ReactiveSecurityContextHolder.getContext()
            .map { securityContext -> securityContext.authentication }
            .filter { authentication -> authentication.isAuthenticated }
            .map { authentication -> authentication.principal as PersonDetails }
            .map { personDetails -> personDetails.person }
            .awaitSingleOrNull()
    }
}