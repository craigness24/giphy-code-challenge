package com.craig.challenge.giphy

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GiphyAppUserTests {

	@Autowired
	lateinit var mockMvc: MockMvc;

	@WithMockUser("USER")
    @Test
    fun `user giphy save happy path`() {
		val saveDTO = SaveDTO("12345")

		mockMvc.post("/user") {
			accept = MediaType.APPLICATION_JSON
			contentType = MediaType.APPLICATION_JSON
			content = jacksonObjectMapper().writeValueAsString(saveDTO)
		}.andExpect {
			status { isOk }
		}

		val expected = "{\"id\":\"USER\",\"giphyIds\":[\"12345\"]}"

		mockMvc.get("/user") {
			accept = MediaType.APPLICATION_JSON
		}.andExpect {
			status { isOk }
			content { json(expected) }
		}
    }

}
