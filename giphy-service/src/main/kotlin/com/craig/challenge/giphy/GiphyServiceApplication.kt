package com.craig.challenge.giphy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@SpringBootApplication
class GiphyServiceApplication {
    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate {
        return builder.build()
    }
}

fun main(args: Array<String>) {
    runApplication<GiphyServiceApplication>(*args)
}

@RestController
class RestController(private val giphyRestClient: GiphyRestClient) {

    @CrossOrigin(origins = ["http://localhost:3000"])
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun get(): String? {
        return giphyRestClient.search()
    }
}

@Component
class GiphyRestClient(private val props: GiphyApiProperties,
                      private val restTemplate: RestTemplate) {
    fun search(): String? {
        return restTemplate.getForObject("${props.url}/search?q=ryan+gosling&api_key=${props.key}&limit=10", String::class.java)
    }
}

@Configuration
@ConfigurationProperties(prefix = "giphy.api")
class GiphyApiProperties() {
    lateinit var key: String;
    lateinit var url: String;
}
