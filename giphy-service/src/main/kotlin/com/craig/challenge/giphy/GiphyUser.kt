package com.craig.challenge.giphy

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*
import javax.persistence.Id


data class SaveDTO(val giphyId: String)


@Document
data class AppUser(@Id val id: String, val username: String, val password: String)

interface UserRepository : MongoRepository<AppUser, String> {
    fun findByUsername(username: String): Optional<AppUser>
}