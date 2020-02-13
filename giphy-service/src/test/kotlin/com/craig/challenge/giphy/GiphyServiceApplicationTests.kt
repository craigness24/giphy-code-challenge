package com.craig.challenge.giphy

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GiphyServiceApplicationTests {

    @Autowired
    lateinit var mockMvc: MockMvc;

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var repository: UserRepository;

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder;

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

    @Test
    fun `as a guest user search`() {
        val password = "testPassword"
        val appUser: AppUser = repository.save(
                AppUser(id = UUID.randomUUID().toString(), username = "Tester", password = passwordEncoder.encode(password)))

        val searchString = "test";
        val mockGiphyResponse = GiphyResponse(
                data = listOf(GiphyObject(id = "12345",
                        images = GiphyImage(fixed_height = GiphyImageDetails(url = "testers.com/image/test/gif")))))

        Mockito.`when`(giphyRestClient.search(searchString))
                .thenReturn(mockGiphyResponse)

        val expected: List<AppGiphy> =
                listOf(AppGiphy(giphyId = mockGiphyResponse.data[0].id,
                        liked = false,
                        url = mockGiphyResponse.data[0].images.fixed_height.url,
                        categories = emptySet()))

        mockMvc.get("/api/search?q=$searchString") {
            accept = MediaType.APPLICATION_JSON
            headers {
                setBasicAuth(appUser.username, password)
            }
        }.andExpect {
            content { contentType(MediaType.APPLICATION_JSON) }
            content { json(objectMapper.writeValueAsString(expected)) }
        }
    }


}
