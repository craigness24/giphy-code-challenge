package com.craig.challenge.giphy

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.security.Principal
import java.util.*

/**
 * Used as an input from the web on the register POST request
 */
data class AccountDTO(val userName: String, val password: String, val passwordCheck: String)

/**
 * Intended to be used as the root account paths for register/login
 */

@RestController
class AccountController(private val repository: UserRepository,
                        private val passwordEncoder: PasswordEncoder) {
    val log: Logger = LoggerFactory.getLogger(this.javaClass.name)

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping(path = ["/register"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun register(@RequestBody accountDTO: AccountDTO) {
        log.info("Attempting to register user ${accountDTO.userName}")
        if (accountDTO.password != accountDTO.passwordCheck) {
            throw RuntimeException("Password check failed");
        }

        repository.save(
                AppUser(id = UUID.randomUUID().toString(),
                        username = accountDTO.userName,
                        password = passwordEncoder.encode(accountDTO.password))
        )
    }

    @GetMapping(path = ["/login"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun login(principal: Principal): UserProfile {
        log.info("Attempting to login ${principal.name}")
        val appUser = repository.findByUsername(principal.name)
                .orElseThrow { RuntimeException("user not found") }

        return UserProfile(id = appUser.id, username = appUser.username, giphys = appUser.giphyCards.values)
    }

}
