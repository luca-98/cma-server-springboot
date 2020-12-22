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

public class ManageSubclinicalTest extends CmaBaseTest {
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
	void getAllClinicalExamStaff() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		mockMvc.perform(get("/staff/get-all-clinical-exam-staff").header(HttpHeaders.AUTHORIZATION, token)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void getAllClinicalExamRoom() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		mockMvc.perform(get("/room-service/get-all-clinical-exam-room").header(HttpHeaders.AUTHORIZATION, token)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void getListMedicalExam() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("fromDate", "14/12/2020");
		requestParams.add("toDate", "14/12/2020");
		requestParams.add("roomId", "13fb5e74-f8d4-4494-9075-f8212708a444");
		requestParams.add("doctorId", "d1841b7c-efe8-463a-aa75-dacf8720f471");
		requestParams.add("status", "-1");
		requestParams.add("clinicalExamCode", "");
		requestParams.add("patientCode", "");
		requestParams.add("phone", "");
		requestParams.add("pageIndex", "0");
		requestParams.add("pageSize", "25");
		mockMvc.perform(get("/medical-examination/get-list-medical-exam").params(requestParams)
				.header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void getNextOrdinalStaff() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("staffId", "d1841b7c-efe8-463a-aa75-dacf8720f471");
		mockMvc.perform(get("/room-service/get-all-clinical-exam-room").params(requestParams)
				.header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void getNextOrdinalStaffNotAuthen() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("staffId", "d1841b7c-efe8-463a-aa75-dacf8720f471");
		mockMvc.perform(get("/room-service/get-all-clinical-exam-room").params(requestParams)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(403));
	}

	@Test
	void getAllGroupService() throws Exception {
		mockMvc.perform(get("/group-service/get-all-group-service").header(HttpHeaders.AUTHORIZATION, token)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void getAllGroupServiceByStaff() throws Exception {
		mockMvc.perform(get("/group-service/get-all-group-service-by-staff").header(HttpHeaders.AUTHORIZATION, token)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void getAllServiceByGroupService() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("groupServiceCode", "ULTRASOUND");
		mockMvc.perform(get("/service/get-all-service-by-group-service").params(requestParams)
				.header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void getAllServiceByGroupService2() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("groupServiceCode", "ENDOSCOPY");
		mockMvc.perform(get("/service/get-all-service-by-group-service").params(requestParams)
				.header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void getAllServiceByGroupService3() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("groupServiceCode", "TEST");
		mockMvc.perform(get("/service/get-all-service-by-group-service").params(requestParams)
				.header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void getAllServiceByGroupService4() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("groupServiceCode", "OTHER");
		mockMvc.perform(get("/service/get-all-service-by-group-service").params(requestParams)
				.header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void getAllServiceByGroupService5() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("groupServiceCode", "SKIN");
		mockMvc.perform(get("/service/get-all-service-by-group-service").params(requestParams)
				.header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void getAllServiceByGroupService6() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("groupServiceCode", "X-RAY");
		mockMvc.perform(get("/service/get-all-service-by-group-service").params(requestParams)
				.header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void getAllServiceByGroupService7() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("groupServiceCode", "RANG_-_HAM_MAT");
		mockMvc.perform(get("/service/get-all-service-by-group-service").params(requestParams)
				.header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void getListForManager() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("fromDate", "14/12/2020");
		requestParams.add("toDate", "14/12/2020");
		requestParams.add("roomId", "13fb5e74-f8d4-4494-9075-f8212708a444");
		requestParams.add("doctorId", "d1841b7c-efe8-463a-aa75-dacf8720f471");
		requestParams.add("status", "-1");
		requestParams.add("clinicalExamCode", "");
		requestParams.add("patientCode", "");
		requestParams.add("phone", "");
		requestParams.add("pageIndex", "0");
		requestParams.add("pageSize", "25");
		mockMvc.perform(
				get("/subclinical/get-list-for-manage").params(requestParams).header(HttpHeaders.AUTHORIZATION, token)
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void getListForManagerBad() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("fromDate", "14/12/2020");
		requestParams.add("toDate", "14/12/2020");
		requestParams.add("roomId", "13fb5e74-f8d4-4494-9075-f8212708a444");
		requestParams.add("doctorId", "d1841b7c-efe8-463a-aa75-dacf8720f471");
		requestParams.add("status", "-1");
		requestParams.add("clinicalExamCode", "");
		requestParams.add("patientCode", "");
		requestParams.add("phone", "");
		requestParams.add("pageIndex", "0");
		requestParams.add("pageSize", "25");
		mockMvc.perform(
				get("/subclinical/get-list-for-manage").header(HttpHeaders.AUTHORIZATION, token)
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(400));
	}
}
