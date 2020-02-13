package com.craig.challenge.giphy

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*
import kotlin.collections.HashMap


@Document
data class AppUser(val id: String,
                   val username: String,
                   val password: String,
                   val giphyCards: Map<String, GiphyCard> = HashMap())

interface UserRepository : MongoRepository<AppUser, String> {
    fun findByUsername(username: String): Optional<AppUser>
}