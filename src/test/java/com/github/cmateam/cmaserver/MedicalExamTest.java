package com.github.cmateam.cmaserver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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

public class MedicalExamTest extends CmaBaseTest {
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
    void getAuthenObj() throws Exception {
        // LinkedMultiValueMap<String, String> requestParams = new
        // LinkedMultiValueMap<>();
        // requestParams.add("groupServiceCode", "ENDOSCOPY");
        mockMvc.perform(get("/staff/get-authen-object/d1841b7c-efe8-463a-aa75-dacf8720f471")
                .header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("OK"))
                .andExpect(jsonPath("$.message.staffId").value("d1841b7c-efe8-463a-aa75-dacf8720f471"));
    }

    @Test
    void getPriceClinicalExamination() throws Exception {
        // LinkedMultiValueMap<String, String> requestParams = new
        // LinkedMultiValueMap<>();
        // requestParams.add("groupServiceCode", "ENDOSCOPY");
        mockMvc.perform(get("/service/get-price-clinical-examination").header(HttpHeaders.AUTHORIZATION, token)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value("OK"));
    }

    @Test
    void checkMedicalExamExist() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("name", "a");
        mockMvc.perform(get("/patient/search-by-name").params(requestParams).header(HttpHeaders.AUTHORIZATION, token)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200)).andExpect(jsonPath("$.error").value("OK"));
    }

    // @Test
    // void searchByName() throws Exception {
    //     LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
    //     requestParams.add("patientCode", "BN301020004");
    //     mockMvc.perform(get("/medical-examination/check-medical-exam-exist-by-patient-code").params(requestParams)
    //             .header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
    //             .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
    //             .andExpect(jsonPath("$.status").value(200)).andExpect(jsonPath("$.error").value("OK"));
    // }

    @Test
    void getAllServiceByGroupServiceCode() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("groupServiceCode", "ENDOSCOPY");
        mockMvc.perform(get("/service/get-all-service-by-group-service").params(requestParams)
                .header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void updateMedicalExam() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("patientCode", "BN301020004");
        requestParams.add("patientName", "Lê Thi Tha Thu");
        requestParams.add("phone", "9737752729");
        requestParams.add("dateOfBirth", "27/9/2020");
        requestParams.add("gender", "1");
        requestParams.add("address", "HN");
        requestParams.add("debt", "800000");
        requestParams.add("examinationReason", "");
        requestParams.add("bloodVessel", "20");
        requestParams.add("bloodPressure", "20");
        requestParams.add("breathing", "20");
        requestParams.add("temperature", "20");
        requestParams.add("height", "20");
        requestParams.add("weight", "20");
        requestParams.add("symptom", "20");
        requestParams.add("summary", "20");
        requestParams.add("mainDisease", "");
        requestParams.add("mainDiseaseCode", "");
        requestParams.add("extraDisease", "");
        requestParams.add("extraDiseaseCode", "");
        mockMvc.perform(post("/medical-examination/update-medical-exam").params(requestParams)
                .header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200)).andExpect(jsonPath("$.error").value("OK"))
                .andExpect(jsonPath("$.message.patient.patientCode").value("BN301020004"));
    }

    
    @Test
    void getPrintForm() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("patientCode", "BN301020004");
        mockMvc.perform(get("/print-form")
                .header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200)).andExpect(jsonPath("$.error").value("OK"));
    }

    @Test
    void getOnePrintForm() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("patientCode", "BN301020004");
        mockMvc.perform(get("/print-form/c7edda3a-fb76-4da0-afa5-59ef71984929")
                .header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200)).andExpect(jsonPath("$.error").value("OK"));
    }

    @Test
    void updateHtmlReport() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("id", "35b5c718-eeb0-4f64-a888-7ec85626082f");
        requestParams.add("htmlReport", "Đây là report được tạo bởi UT");
        mockMvc.perform(put("/medical-examination/update-html-report").params(requestParams)
                .header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200)).andExpect(jsonPath("$.error").value("OK"));
    }

    @Test
    void changeStatus() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("id", "35b5c718-eeb0-4f64-a888-7ec85626082f");
        requestParams.add("status", "1");
        mockMvc.perform(put("/medical-examination/change-status").params(requestParams)
                .header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200)).andExpect(jsonPath("$.error").value("OK"));
    }

    @Test
    void getAllGroupService() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("id", "35b5c718-eeb0-4f64-a888-7ec85626082f");
        requestParams.add("status", "1");
        mockMvc.perform(get("/group-service/get-all-group-service").params(requestParams)
                .header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200)).andExpect(jsonPath("$.error").value("OK"));
    }
}
