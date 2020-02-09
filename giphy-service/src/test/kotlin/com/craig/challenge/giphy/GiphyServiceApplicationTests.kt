package com.craig.challenge.giphy

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MockMvcBuilder
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GiphyServiceApplicationTests {

	@Autowired
	lateinit var mockMvc: MockMvc;

	@MockBean
	lateinit var giphyRestClient: GiphyRestClient

	lateinit var context: WebApplicationContext

	@BeforeAll
	fun beforeAll() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.build()
	}

    @Test
    fun contextLoads() {
		val expected = "{ sample: 5 }"
		Mockito.`when`(giphyRestClient.search("test"))
				.thenReturn(expected)

		mockMvc.get("/test") {
			contentType = MediaType.APPLICATION_JSON
			accept = MediaType.APPLICATION_JSON
		}.andExpect {
			status { isOk }
			content { contentType(MediaType.APPLICATION_JSON) }
			content { json(expected) }
		}
    }

}
