package com.craig.challenge.giphy

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import java.util.*

@SpringBootApplication
class GiphyServiceApplication {
    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate {
        return builder.build()
    }

    @Bean
    fun init(passwordEncoder: PasswordEncoder, repository: UserRepository) = CommandLineRunner {
        val appUser = AppUser(id = UUID.randomUUID().toString(), username = "user1", password = passwordEncoder.encode("password1"))
        repository.save(appUser)
    }
}

fun main(args: Array<String>) {
    runApplication<GiphyServiceApplication>(*args)
}


@CrossOrigin(origins = ["http://localhost:3000"])
@RestController
@RequestMapping("/api")
class GiphyController(private val giphyRestClient: GiphyRestClient) {

    @GetMapping(path = ["/search"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun get(@RequestParam("q") searchString: String): String? {
        return giphyRestClient.search(searchString)
    }

    @GetMapping("/api/health")
    fun hello(): Health {
        return Health.UP
    }

}

data class Health(val status: String) {
    companion object {
        val UP = Health("UP")
    }
}

@Component
class GiphyRestClient(private val props: GiphyApiProperties,
                      private val restTemplate: RestTemplate) {
    fun search(searchString: String): String? {
        val url = "${props.url}/search?q=${searchString}&api_key=${props.key}&limit=10&rating=${props.rating}"
        return restTemplate.getForObject(url, String::class.java)
    }
}

@Configuration
@ConfigurationProperties(prefix = "giphy.api")
class GiphyApiProperties() {
    lateinit var key: String;
    lateinit var url: String;
    lateinit var rating: String;
}

