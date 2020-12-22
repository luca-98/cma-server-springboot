package com.github.cmateam.cmaserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import com.github.cmateam.cmaserver.entity.AppUserEntity;
import com.github.cmateam.cmaserver.repository.AppUserRepository;
import com.github.cmateam.cmaserver.service.TokenAuthenticationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;

public class ManageMedicalExamTest extends CmaBaseTest {
	private String token;
	private UUID userId = UUID.fromString("87a5d03f-e810-42cf-8ef9-5c92a5302790");
	private AppUserEntity mockUser;

	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;

	@Autowired
	private AppUserRepository appUserRepository;

	@BeforeEach
	public void setUp() throws Exception {
		mockUser = appUserRepository.getOne(userId);
		token = "Bearer" + tokenAuthenticationService.generateToken(mockUser.getUserName());
	}
	
	@Test
	void getAllDisease() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("icd10Code", "A");
		mockMvc.perform(get("/disease/get-disease-by-code").params(requestParams)
				.header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	void getMedicalExamById() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("id", "b712f15c-f3bd-4e35-a318-8fa72ee39536");
		mockMvc.perform(get("/medical-examination/get-medical-exam")
				.header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(400));
	}
	
	@Test
	void getHistory() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("id", "5c6b9c29-d9cd-4a49-bd28-c184f302f129");
		mockMvc.perform(get("/patient/get-history").params(requestParams)
				.header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	// -----

	@Test
	void getAllDiseaseBad() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("icd10Code", "A");
		mockMvc.perform(get("/disease/get-disease-by-code")
				.header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(400));
	}
	
	@Test
	void getHistorBady() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("id", "5c6b9c29-d9cd-4a49-bd28-c184f302f129");
		mockMvc.perform(get("/patient/get-history")
				.header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(400));
	}
}
