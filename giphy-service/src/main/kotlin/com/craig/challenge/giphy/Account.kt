package com.craig.challenge.giphy

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*


data class Account(val userName: String, val password: String)

@CrossOrigin(origins = ["http://localhost:3000"])
@RestController
@RequestMapping("/account")
class AccountController(private val repository: UserRepository) {
    // create
    @RequestMapping(method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun register(@RequestBody account: Account) {
        // load random id from giphy
        repository.save(User(username = account.userName, password = ))
    }

    // login
    // logout
    // delete
}