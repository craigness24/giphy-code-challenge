package com.craig.challenge.giphy

import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*
import javax.persistence.Id


data class SaveDTO(val giphyId: String)



data class User(@Id val id: String, val username: String, val password: String)

interface UserRepository: MongoRepository<User, String> {
    fun findByUsername(username: String): Optional<User>
}