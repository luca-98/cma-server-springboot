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

public class ManageFinanceTest extends CmaBaseTest {

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
    void getInvoiceByPatientId() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("patientId", "52850fb6-3d01-4c52-8fde-faf0c086d3c6");
        mockMvc.perform(
                get("/invoice/get-invoice-by-patientId").params(requestParams).header(HttpHeaders.AUTHORIZATION, token)
                        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getInvoiceByPatientIdBad() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("patientId", "52850fb6-3d01-4c52-8fde-faf0c086d3c6");
        mockMvc.perform(get("/invoice/get-invoice-by-patientId").header(HttpHeaders.AUTHORIZATION, token)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    void searchByName() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("name", "nguyen");
        mockMvc.perform(get("/invoice/search-by-name").params(requestParams).header(HttpHeaders.AUTHORIZATION, token)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void searchByNameBad() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("name", "nguyen");
        mockMvc.perform(get("/invoice/search-by-name").header(HttpHeaders.AUTHORIZATION, token)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    void searchByPhone() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("phone", "0962481492");
        mockMvc.perform(get("/invoice/search-by-phone").params(requestParams).header(HttpHeaders.AUTHORIZATION, token)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void searchByPhoneBad() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("phone", "0962481492");
        mockMvc.perform(get("/invoice/search-by-phone").header(HttpHeaders.AUTHORIZATION, token)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    void searchByPatientCode() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("patientCode", "BN291020002");
        mockMvc.perform(get("/invoice/search-by-code").params(requestParams).header(HttpHeaders.AUTHORIZATION, token)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void updateInformationInvoice() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("patientId", "52850fb6-3d01-4c52-8fde-faf0c086d3c6");
        mockMvc.perform(
                get("/invoice/get-invoice-by-patientId").params(requestParams).header(HttpHeaders.AUTHORIZATION, token)
                        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateInformationInvoiceBad() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("patientId", "52850fb6-3d01-4c52-8fde-faf0c086d3c6");
        mockMvc.perform(get("/invoice/get-invoice-by-patientId").header(HttpHeaders.AUTHORIZATION, token)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @Test
    void getVoucherNumber() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        mockMvc.perform(get("/receipt-voucher/get-number-voucher").params(requestParams)
                .header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void getLstVoucherType() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        mockMvc.perform(
                get("/voucher-type/get-voucher-type").params(requestParams).header(HttpHeaders.AUTHORIZATION, token)
                        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getLstVoucherTypeNotAuthen() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        mockMvc.perform(get("/voucher-type/get-voucher-type").params(requestParams).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(403));
    }

    @Test
    void getVoucherNumberPay() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        mockMvc.perform(get("/debt-payment-slip/get-number-voucher-pay").params(requestParams)
                .header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void getAllVoucher() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        mockMvc.perform(
                get("/manage-voucher/get-all-voucher").params(requestParams).header(HttpHeaders.AUTHORIZATION, token)
                        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // --------------

    @Test
    void getAllReceiptSupplierDebtByid() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("supplierId", "d9c3451e-30b0-4f2c-8cd8-3088a6351cd1");
        requestParams.add("pageIndex", "0");
        requestParams.add("pageSize", "25");
        mockMvc.perform(get("/debt-payment-slip/get-all-receipt-debt-by-supplierId").params(requestParams)
                .header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void getAllReceiptSupplierDebtByidBad() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("supplierId", "d9c3451e-30b0-4f2c-8cd8-3088a6351cd1");
        requestParams.add("pageIndex", "0");
        requestParams.add("pageSize", "25");
        mockMvc.perform(get("/debt-payment-slip/get-all-receipt-debt-by-supplierId")
                .header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(400));
    }

    @Test
    void searchByNameSupplier() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("supplierNameSearch", "B");
        mockMvc.perform(get("/debt-payment-slip/search-by-name-supplier").params(requestParams)
                .header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void searchByNameSupplierBad() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("supplierNameSearch", "B");
        mockMvc.perform(get("/debt-payment-slip/search-by-name-supplier")
                .header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(400));
    }

    @Test
    void searchByPhoneSupplier() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("phone", "0289000086");
        mockMvc.perform(get("/debt-payment-slip/search-by-name-phone").params(requestParams)
                .header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void searchByPhoneSupplierBad() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("phone", "0289000086");
        mockMvc.perform(get("/debt-payment-slip/search-by-name-phone")
                .header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(400));
    }

    @Test
    void searchDebtByPatientCode() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("patientCode", "A");
        mockMvc.perform(
                get("/debt-payment-slip/search-by-code").params(requestParams).header(HttpHeaders.AUTHORIZATION, token)
                        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void searchDebtByName() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("name", "ba van bon");
        mockMvc.perform(
                get("/debt-payment-slip/search-by-name").params(requestParams).header(HttpHeaders.AUTHORIZATION, token)
                        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAllInvoiceDetailPatientDebtByid() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("patientId", "99894e24-bc02-4148-9b4c-a8e48db40989");
        requestParams.add("pageSize", "25");
        requestParams.add("pageIndex", "0");
        mockMvc.perform(get("/debt-payment-slip/get-all-invoice-detail-debt-by-patientId").params(requestParams)
                .header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void getAllInvoiceDetailPatientDebtByidBad() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("patientId", "99894e24-bc02-4148-9b4c-a8e48db40989");
        requestParams.add("pageSize", "25");
        requestParams.add("pageIndex", "0");
        mockMvc.perform(get("/debt-payment-slip/get-all-invoice-detail-debt-by-patientId")
                .header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(400));
    }

    @Test
    void getAllStaff() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        mockMvc.perform(get("/staff/get-all-staff").params(requestParams).header(HttpHeaders.AUTHORIZATION, token)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void getVoucherById() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("idVoucher", "93dd1023-86f6-4dd1-8545-d38c8695729a");
        mockMvc.perform(get("/manage-voucher/get-detal-voucher-by-id").params(requestParams)
                .header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void getVoucherByIdBad() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("idVoucher", "93dd1023-86f6-4dd1-8545-d38c8695729a");
        mockMvc.perform(get("/manage-voucher/get-detal-voucher-by-id")
                .header(HttpHeaders.AUTHORIZATION, token).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(400));
    }
}
