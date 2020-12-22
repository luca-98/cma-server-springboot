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

public class ReportTest extends CmaBaseTest {
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
	void getYearReport() throws Exception {
		mockMvc.perform(get("/report-sum/get-year-report").header(HttpHeaders.AUTHORIZATION, token)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void showReportSum() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("fromDate", "14/12/2020");
		requestParams.add("toDate", "14/12/2020");
		requestParams.add("year", "2020");
		requestParams.add("type", "2");
		mockMvc.perform(
				get("/report-sum/show-report-sum").params(requestParams).header(HttpHeaders.AUTHORIZATION, token)
						.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void getYearReportAuthen() throws Exception {
		mockMvc.perform(get("/report-sum/get-year-report").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(403));
	}

	@Test
	void showReportSumBad() throws Exception {
		LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
		requestParams.add("fromDate", "14/12/2020");
		requestParams.add("toDate", "14/12/2020");
		requestParams.add("year", "2020");
		requestParams.add("type", "2");
		mockMvc.perform(get("/report-sum/show-report-sum").header(HttpHeaders.AUTHORIZATION, token)
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(400));
	}
}
