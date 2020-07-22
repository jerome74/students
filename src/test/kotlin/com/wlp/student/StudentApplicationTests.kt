package com.wlp.student

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest
class StudentApplicationTests(@Autowired val mockMvc: MockMvc) {


	@Autowired
	lateinit var objectMapper : ObjectMapper

	@MockkBean
	lateinit var studentRepository : StudentRepository


	@Test
	fun getAllStudents(){

		val asabetta = Student(1 , "Alessandro SABETTA")
		val vvaccaro = Student(2, "Valentina VACCARO")
		val ngraziano = Student(3, "Nicola Graziano")
		every { studentRepository.findAll() } returns listOf(asabetta, vvaccaro,ngraziano)

		mockMvc.get("http://localhost:8083/api/v1/students")
		{
			accept = MediaType.APPLICATION_JSON
			with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "password"))
		}.andExpect {
			status { isOk }
			content { contentType(MediaType.APPLICATION_JSON) }
			content { MockMvcResultMatchers.jsonPath("$.[0].id").isNotEmpty() }
		}

	}

	@Test
	fun getStudent() {

		val asabetta = Student(1 , "Alessandro SABETTA")
		val vvaccaro = Student(2, "Valentina VACCARO")
		val ngraziano = Student(3, "Nicola Graziano")

		every { studentRepository.getOne(1) } returns asabetta
		every { studentRepository.getOne(2) } returns vvaccaro
		every { studentRepository.getOne(3) } returns ngraziano

		val studentId = (1 until 3).random()

		mockMvc.get("http://localhost:8083/api/v1/students/$studentId")
		{
			accept = MediaType.APPLICATION_JSON
			with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "password"))
		}.andExpect {
			status { isOk }
			content { contentType(MediaType.APPLICATION_JSON) }
		}

	}


	@Test
	fun registerStudent(){

		val asabetta = Student(1 , "Alessandro SABETTA")
		every { studentRepository.save(asabetta) } returns asabetta

		mockMvc.post("http://localhost:8083/api/v1/students")
		{
			contentType = MediaType.APPLICATION_JSON
			content = objectMapper.writeValueAsString(Student(1 , "Alessandro SABETTA"))
			accept = MediaType.APPLICATION_JSON
			with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "password"))
		}.andExpect {
			status { isOk }
			content { contentType(MediaType.APPLICATION_JSON) }
			content { MockMvcResultMatchers.jsonPath("$.id").value(1) }

		}

	}

	@Test
	fun updateStudent(){

		val asabetta = Student(1 , "Alessandro SABETTA")
		val vvaccaro = Student(2, "Valentina VACCARO")
		val ngraziano = Student(3, "Nicola Graziano")
		every { studentRepository.getOne(1) } returns asabetta
		every { studentRepository.getOne(2) } returns vvaccaro
		every { studentRepository.getOne(3) } returns ngraziano
		every { studentRepository.save(asabetta) } returns asabetta
		every { studentRepository.save(vvaccaro) } returns vvaccaro
		every { studentRepository.save(ngraziano) } returns ngraziano

		val studentId = (1 until 3).random()

		mockMvc.put("http://localhost:8083/api/v1/students/$studentId")
		{
			contentType = MediaType.APPLICATION_JSON
			content = objectMapper.writeValueAsString(Student(1 , "Alessandro SABETTA"))
			accept = MediaType.APPLICATION_JSON
			with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "password"))
		}.andExpect {
			status { isOk }
			content { contentType(MediaType.APPLICATION_JSON) }
			content { MockMvcResultMatchers.jsonPath("$.id").isNotEmpty }

		}

	}

}
