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

public class ManageMedicineTest extends CmaBaseTest {

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
	void getAllMedicineSale() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("pageSize", "25");
		requestParams.add("pageIndex", "0");
		mockMvc.perform(get("/medicine-sale/get-all-medicine-sale").params(requestParams)
				.header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void searchMedicineSaleByName() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("medicineName", "a");
		mockMvc.perform(
				get("/medicine/search-medicine-by-name").params(requestParams).header(HttpHeaders.AUTHORIZATION, token)
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void getListPrescriptionById() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("patientId", "30f1c966-c3b5-4615-be40-3530c0da7050");
		mockMvc.perform(get("/medicine-sale/get-list-prescription-by-patientId").params(requestParams)
				.header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void getPrescriptionById() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("prescriptionId", "34fa7680-e933-48ae-b346-814373fde364");
		mockMvc.perform(get("/medicine-sale/get-prescription-by-id").params(requestParams)
				.header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void getAllGroupMaterial() throws Exception {
		mockMvc.perform(get("/group-material/get-all-group-material").header(HttpHeaders.AUTHORIZATION, token)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void getAllGroupMedicine() throws Exception {
		mockMvc.perform(get("/group-medicine/get-all-group-medicine").header(HttpHeaders.AUTHORIZATION, token)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void searchAutByName() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("name", "duc");
		mockMvc.perform(get("/patient/search-by-name").params(requestParams).header(HttpHeaders.AUTHORIZATION, token)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	//------

	@Test
	void getAllMedicineSaleBad() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("pageSize", "25");
		requestParams.add("pageIndex", "0");
		mockMvc.perform(get("/medicine-sale/get-all-medicine-sale")
				.header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(400));
	}

	@Test
	void searchMedicineSaleByNameBad() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("medicineName", "a");
		mockMvc.perform(
				get("/medicine/search-medicine-by-name").header(HttpHeaders.AUTHORIZATION, token)
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(400));
	}

	@Test
	void getListPrescriptionByIdBad() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("patientId", "30f1c966-c3b5-4615-be40-3530c0da7050");
		mockMvc.perform(get("/medicine-sale/get-list-prescription-by-patientId")
				.header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(400));
	}

	@Test
	void getPrescriptionByIBad() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("prescriptionId", "34fa7680-e933-48ae-b346-814373fde364");
		mockMvc.perform(get("/medicine-sale/get-prescription-by-id")
				.header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(400));
	}

	@Test
	void searchAutByNameBad() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("name", "duc");
		mockMvc.perform(get("/patient/search-by-name").header(HttpHeaders.AUTHORIZATION, token)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(400));
	}
}
