package com.craig.challenge.giphy

import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.security.Principal
import java.util.*

data class ApplicationUser(val username: String, val password: String)

data class Account(val userName: String, val password: String, val passwordCheck: String)

@CrossOrigin(origins = ["http://localhost:3000"])
@RestController
class AccountController(private val repository: UserRepository,
                        private val passwordEncoder: PasswordEncoder) {
    // create
    @PostMapping(path = ["/register"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun register(@RequestBody account: Account) {
        // load random id from giphy
        repository.save(
                AppUser(id = UUID.randomUUID().toString(),
                        username = account.userName,
                        password = passwordEncoder.encode(account.password))
        )
    }

    @GetMapping(path = ["/login"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun login(principal: Principal): AppUser {
        return repository.findByUsername(principal.name)
                .orElseThrow { RuntimeException("not found") }
    }

    @RequestMapping(path = ["/me"], method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun me(principal: Principal): AppUser {
        return repository.findByUsername(principal.name)
                .orElseThrow { RuntimeException("not found") }
    }
}