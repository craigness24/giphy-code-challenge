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

data class AppGiphy(val giphyId: String,
                    val liked: Boolean = false,
                    val url: String,
                    val categories: Set<String> = HashSet())

@RestController
@RequestMapping("/api")
class GiphyController(
        private val giphyRestClient: GiphyRestClient,
        private val userRepository: UserRepository) {

    @GetMapping(path = ["/giphys"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun get(principal: Principal): List<AppGiphy> {
        val dbGiphys: Map<String, GiphyCard> = userRepository.findByUsername(principal.name)
                .map { it.giphyCards }
                .orElse(emptyMap())

        val giphySearch: GiphyResponse? = giphyRestClient.byIds(dbGiphys.keys)
        return mergeGiphys(giphySearch, dbGiphys)
    }

    /**
     * Merge the giphy result set with the saved profile data if any, that way we can show the liked and categories
     * on the search results
     */
    @GetMapping(path = ["/search"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun get(@RequestParam("q") searchString: String, principal: Principal): List<AppGiphy> {
        val giphySearch: GiphyResponse? = giphyRestClient.search(searchString)
        val dbGiphys: Map<String, GiphyCard> = userRepository.findByUsername(principal.name)
                .map { it.giphyCards }
                .orElse(emptyMap())

        return mergeGiphys(giphySearch, dbGiphys);
    }

    private fun mergeGiphys(giphySearch: GiphyResponse?, dbGiphys: Map<String, GiphyCard>): List<AppGiphy> {
        return giphySearch?.data
                ?.map {
                    val get: GiphyCard? = dbGiphys[it.id]
                    if (get != null) {
                        AppGiphy(giphyId = it.id, liked = true, url = it.images.fixed_height.url, categories = get.categories)
                    } else {
                        AppGiphy(giphyId = it.id, liked = false, url = it.images.fixed_height.url, categories = emptySet())
                    }
                }
                ?: emptyList()
    }
}

@Component
class GiphyRestClient(private val props: GiphyApiProperties,
                      private val restTemplate: RestTemplate) {
    fun search(searchString: String): GiphyResponse? {
        val url = "${props.url}/search?q=$searchString&api_key=${props.key}&limit=10&rating=${props.rating}"
        return restTemplate.getForObject(url, GiphyResponse::class)
    }

    fun byIds(keys: Set<String>): GiphyResponse? {
        val idCsv = keys.joinToString(",")
        val url = "${props.url}?api_key=${props.key}&ids=$idCsv"
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

