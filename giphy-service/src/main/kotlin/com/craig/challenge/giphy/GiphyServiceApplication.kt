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
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import java.security.Principal
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

data class GiphyImageDetails(val url: String)
data class GiphyImage(val fixed_height: GiphyImageDetails)
data class GiphyObject(val id: String, val images: GiphyImage)
data class GiphyResponse(val data: List<GiphyObject>)

data class AppGiphys(val giphyId: String,
                     val liked: Boolean = false,
                     val url: String,
                     val categories: Set<String> = HashSet())

@RestController
@RequestMapping("/api")
class GiphyController(
        private val giphyRestClient: GiphyRestClient,
        private val userRepository: UserRepository) {

    /**
     * Merge the giphy result set with the saved profile data if any, that way we can show the liked and categories
     * on the search results
     */
    @GetMapping(path = ["/search"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun get(@RequestParam("q") searchString: String, principal: Principal): List<AppGiphys> {
        val giphySearch: GiphyResponse? = giphyRestClient.search(searchString)
        val dbGiphys: Map<String, GiphyCard> = userRepository.findByUsername(principal.name)
                .map { it.giphyCards }
                .orElse(emptyMap())

        val convertedMap = giphySearch?.data
                ?.map {
                        val get: GiphyCard? = dbGiphys[it.id]
                        if (get != null) {
                            AppGiphys(giphyId = it.id, liked = true, url = it.images.fixed_height.url, categories = get.categories)
                        } else {
                            AppGiphys(giphyId = it.id, liked = false, url = it.images.fixed_height.url, categories = emptySet())
                        }
                }
                ?: emptyList()

        return convertedMap;
    }
}

@Component
class GiphyRestClient(private val props: GiphyApiProperties,
                      private val restTemplate: RestTemplate) {
    fun search(searchString: String): GiphyResponse? {
        val url = "${props.url}/search?q=${searchString}&api_key=${props.key}&limit=10&rating=${props.rating}"
        return restTemplate.getForObject(url, GiphyResponse::class)
    }
}

@Configuration
@ConfigurationProperties(prefix = "giphy.api")
class GiphyApiProperties() {
    lateinit var key: String;
    lateinit var url: String;
    lateinit var rating: String;
}

