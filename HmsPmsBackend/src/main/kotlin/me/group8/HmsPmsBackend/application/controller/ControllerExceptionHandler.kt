package me.group8.HmsPmsBackend.application.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerExceptionHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleException(ex: Exception): ResponseEntity<String> {
        ex.printStackTrace()
        if(ex is AuthenticationException) {
            return ResponseEntity.badRequest().body("Failed to authenticate. Bad Credentials")
        }

        return ResponseEntity.badRequest().body("Unexpected error occurred")
    }
}