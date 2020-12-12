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

public class ReceivePatientTest extends CmaBaseTest {

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
	void addPatientReceive() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("patientCode", "BN291020000");
		requestParams.add("patientName", "Đỗ Trung Đức");
		requestParams.add("phone", "0962481497");
		requestParams.add("dateOfBirth", "1/2/1998");
		requestParams.add("gender", "0");
		requestParams.add("address", "Hải Phòng");
		requestParams.add("ordinalNumber", "3");
		requestParams.add("clinicalExamPrice", "250,000");
		requestParams.add("roomServiceId", "8c2ec956-11ee-44d5-b6f6-e94deb94c596");
		requestParams.add("staffId", "d1841b7c-efe8-463a-aa75-dacf8720f471");
		requestParams.add("debt", "0");
		requestParams.add("examinationReason", "aa");
		mockMvc.perform(post("/receive-patient/add-patient-receive").params(requestParams)
				.header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void getListPatientReceive() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("pageSize", "10");
		requestParams.add("pageIndex", "1");
		mockMvc.perform(get("/receive-patient/get-patient-receive").params(requestParams)
				.header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
}
