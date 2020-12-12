package com.github.cmateam.cmaserver.controller;

import java.security.Principal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.dto.InfoSubclinicalDTO;
import com.github.cmateam.cmaserver.dto.MedicalExamTableDTO;
import com.github.cmateam.cmaserver.dto.StaffDTO;
import com.github.cmateam.cmaserver.dto.SubclinicalPaggingDTO;
import com.github.cmateam.cmaserver.service.SubclinicalServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("subclinical")
public class SubclinicalController {

    private SubclinicalServiceImpl subclinicalServiceImpl;

    @Autowired
    public SubclinicalController(SubclinicalServiceImpl subclinicalServiceImpl) {
        this.subclinicalServiceImpl = subclinicalServiceImpl;
    }

    @GetMapping(value = "/get-staff-min-service")
    public StaffDTO getStaffMinByService(@RequestParam String groupServiceCode) {
        return subclinicalServiceImpl.getStaffMinByService(groupServiceCode);
    }

    @GetMapping("get-init-info-appoint")
    public InfoSubclinicalDTO getInitInfoAppoint(@RequestParam UUID medicalExamId) {
        return subclinicalServiceImpl.getInitInfoAppoint(medicalExamId);
    }

    @PostMapping("/save-appoint-subclinical")
    public Boolean saveAppointSubclinical(@RequestBody InfoSubclinicalDTO info) throws ParseException {
        return subclinicalServiceImpl.saveAppointSubclinical(info);
    }

    @PostMapping("update-subclinical")
    public InfoSubclinicalDTO updateSubclinical(@RequestBody InfoSubclinicalDTO info, Principal principal)
            throws ParseException {
        String username = principal.getName();
        return subclinicalServiceImpl.updateSubclinical(info, username);
    }

    @GetMapping("init-info-subclinical")
    public ResponseEntity<?> initInfoSubclinical(@RequestParam UUID medicalExamId) {
        return subclinicalServiceImpl.getInitInfoSubclinical(medicalExamId);
    }

    @GetMapping("get-status-paying-subclinical")
    public Boolean getStatusPayingSubclinical(@RequestParam UUID medicalExamId) {
        return subclinicalServiceImpl.getStatusPaying(medicalExamId);
    }

    @GetMapping("get-list-medical-exam-today")
    public List<MedicalExamTableDTO> getListMedicalExamToday(@RequestParam String patientCode) {
        return subclinicalServiceImpl.getListMedicalExamByPatientCode(patientCode);
    }

    @GetMapping("get-list-for-manage")
    public SubclinicalPaggingDTO getListMedicalExam(
            @RequestParam("fromDate") @DateTimeFormat(pattern = "dd/MM/yyyy") Date fromDate,
            @RequestParam("toDate") @DateTimeFormat(pattern = "dd/MM/yyyy") Date toDate,
            @RequestParam("roomId") UUID roomId, @RequestParam("doctorId") UUID doctorId,
            @RequestParam("status") Integer status, @RequestParam("clinicalExamCode") String clinicalExamCode,
            @RequestParam("patientCode") String patientCode, @RequestParam("phone") String phone,
            @RequestParam("pageIndex") Integer pageIndex, @RequestParam("pageSize") Integer pageSize) {
        return subclinicalServiceImpl.getListMedicalExam(fromDate, toDate, roomId, doctorId, status, clinicalExamCode,
                patientCode, phone, pageIndex, pageSize);
    }
}
