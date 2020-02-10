package com.craig.challenge.giphy

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTests(@Autowired val restTemplate: TestRestTemplate) {

    @MockBean
    lateinit var giphyRestClient: GiphyRestClient

    @Test
    fun `Assert public search status is 200`() {
        val expected = "{ sample: 5 }"
        Mockito.`when`(giphyRestClient.search("test"))
                .thenReturn(expected)

        val entity = restTemplate.getForEntity<String>("/api/test")
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).contains(expected)
    }

}