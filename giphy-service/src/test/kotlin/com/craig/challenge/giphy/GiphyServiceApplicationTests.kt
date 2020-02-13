package com.craig.challenge.giphy

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GiphyServiceApplicationTests {

    @Autowired
    lateinit var mockMvc: MockMvc;

    @MockBean
    lateinit var giphyRestClient: GiphyRestClient

    @Test
    fun `api search happy path`() {
        val searchString = "cow"
        val expected = GiphyResponse(data = emptyList())
        Mockito.`when`(giphyRestClient.search(searchString))
                .thenReturn(expected)

        mockMvc.get("/api/search?q=${searchString}") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk }
            content { contentType(MediaType.APPLICATION_JSON) }
//            content { json(expected) }
        }
    }

    @Test
    fun `api search no query param`() {
        mockMvc.get("/api/search") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isBadRequest }
            status { reason("Required String parameter 'q' is not present") }
        }
    }

    @Test
    fun `api search empty query param`() {
        val expected = GiphyResponse(data = emptyList())

        Mockito.`when`(giphyRestClient.search(""))
                .thenReturn(expected)

        mockMvc.get("/api/search?q=") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            content { contentType(MediaType.APPLICATION_JSON) }
//            content { expected }
        }
    }
}
