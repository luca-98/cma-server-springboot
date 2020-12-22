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

public class ServiceTest extends CmaBaseTest {

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
	void getAllGroupService() throws Exception {
		mockMvc.perform(get("/group-service/get-all-group-service").header(HttpHeaders.AUTHORIZATION, token)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void getAllServicePagging() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("pageIndex", "0");
		requestParams.add("pageSize", "25");
		mockMvc.perform(
				get("/service/get-all-service-pagging").params(requestParams).header(HttpHeaders.AUTHORIZATION, token)
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void getListRoomServiceByGroupService() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("groupServiceCode", "ULTRASOUND");
		mockMvc.perform(get("/room-service/get-list-room-service-by-group-service").params(requestParams)
				.header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void getAllServicePaggingBad() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("pageIndex", "0");
		requestParams.add("pageSize", "25");
		mockMvc.perform(
				get("/service/get-all-service-pagging").header(HttpHeaders.AUTHORIZATION, token)
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(400));
	}

	@Test
	void getListRoomServiceByGroupServiceBad() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("groupServiceCode", "ULTRASOUND");
		mockMvc.perform(get("/room-service/get-list-room-service-by-group-service")
				.header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(200));
	}
}
