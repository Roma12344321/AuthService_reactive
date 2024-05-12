package com.martynov.spring.util

class TokenHasExpiredException(override val message : String) : RuntimeException(message) {
}