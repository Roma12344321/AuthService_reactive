package com.martynov.spring.models

import jakarta.validation.constraints.Email
import org.springframework.data.annotation.Id

data class Person(
    @Id
    val id : Int?,
    val username : String?,
    var password : String?,
    @Email
    val email : String?,
    var role : String?
)