package com.craig.challenge.giphy

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import java.net.URI
import javax.servlet.http.Cookie


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LoggedInUserIntegrationTests(@Autowired val restTemplate: TestRestTemplate) {

    data class LoggedInTestSubject(val appUser:AppUser, val cookie: Cookie)

    @Bean
    @Primary
    fun testRestTemplate(): TestRestTemplate {
        return TestRestTemplate("Tester", "TestPassword", TestRestTemplate.HttpClientOption.ENABLE_COOKIES)
    }

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockBean
    lateinit var giphyRestClient: GiphyRestClient

    @BeforeAll
    fun beforeAll(){
//        val password = "testPassword"
//        val appUser: AppUser = repository.save(
//                AppUser(id = UUID.randomUUID().toString(), username = "Tester", password = passwordEncoder.encode(password)))

        val headers = HttpHeaders()
        val accountDTO = AccountDTO(userName = "Tester", password = "TestPassword", passwordCheck = "TestPassword")
        val request = HttpEntity(accountDTO, headers)

        val result = this.restTemplate.postForEntity(URI("/register"), request, String::class.java)

        val forEntity = this.restTemplate.getForEntity("/user", String::class.java)
        val other = forEntity
//        restTemplate.postForObject("/register")
//        val loginResult: MvcResult = mockMvc.get("/login") {
//            accept = MediaType.APPLICATION_JSON
//            contentType = MediaType.APPLICATION_JSON
//            headers {
//                setBasicAuth(appUser.username, password)
//            }
//        }.andExpect {
//            content { contentType(MediaType.APPLICATION_JSON) }
//            status { isOk }
//        }.andReturn()
//
//        val cookie: Cookie = loginResult.response.cookies.first { c -> c.name === "JSESSIONID" }
//        Assert.notNull(cookie, "logged in cookie should not be empty")
//
//        testSubject = LoggedInTestSubject(appUser = appUser, cookie = cookie)
    }

    @Test
    fun `as logged in user like a gif`() {
        val searchString = "cowabunga";
//        val mockGiphyResponse = GiphyResponse(
//                data = listOf(GiphyObject(id = "12345",
//                        images = GiphyImage(fixed_height = GiphyImageDetails(url = "testers.com/image/test/gif")))))
//
//        Mockito.`when`(giphyRestClient.search(searchString))
//                .thenReturn(mockGiphyResponse)
//
//        val expected: List<AppGiphys> =
//                listOf(AppGiphys(giphyId = mockGiphyResponse.data[0].id,
//                        liked = false,
//                        url = mockGiphyResponse.data[0].images.fixed_height.url,
//                        categories = emptySet()))
//
//        mockMvc.post("/user/like?q=$searchString") {
//            accept = MediaType.APPLICATION_JSON
//            contentType = MediaType.APPLICATION_JSON
//            cookie(testSubject.cookie)
//        }.andExpect {
//            content { contentType(MediaType.APPLICATION_JSON) }
//            status { isOk }
//        }
    }
}
