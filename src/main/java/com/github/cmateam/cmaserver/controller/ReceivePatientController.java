package com.github.cmateam.cmaserver.controller;

import java.security.Principal;
import java.util.Date;
import java.util.UUID;

import com.github.cmateam.cmaserver.dto.PatientDTO;
import com.github.cmateam.cmaserver.dto.ReceivePatientDTO;
import com.github.cmateam.cmaserver.dto.ReceivePatientPaggingDTO;
import com.github.cmateam.cmaserver.service.ReceivePatientServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/receive-patient")
public class ReceivePatientController {

	private ReceivePatientServiceImpl receivePatientServiceImpl;

	@Autowired
	public ReceivePatientController(ReceivePatientServiceImpl receivePatientServiceImpl) {
		this.receivePatientServiceImpl = receivePatientServiceImpl;
	}

	@PostMapping("/add-patient-receive")
	public ReceivePatientDTO addPatientReceive(
			@RequestParam(value = "patientCode", required = false) String patientCode,
			@RequestParam("patientName") String patientName, @RequestParam("phone") String phone,
			@RequestParam("dateOfBirth") @DateTimeFormat(pattern = "dd/MM/yyyy") Date dateOfBirth,
			@RequestParam("gender") Integer gender, @RequestParam("address") String address,
			@RequestParam("ordinalNumber") Short ordinalNumber,
			@RequestParam("clinicalExamPrice") String clinicalExamPrice,
			@RequestParam("roomServiceId") UUID roomServiceId, @RequestParam("staffId") UUID staffId,
			@RequestParam("debt") Long debt, @RequestParam("examinationReason") String examinationReason,
			Principal principal) {
		String username = principal.getName();
		return receivePatientServiceImpl.receivePatient(patientCode, patientName, phone, dateOfBirth, gender, address,
				ordinalNumber, clinicalExamPrice, roomServiceId, staffId, debt, examinationReason, username, staffId);
	}

	@PutMapping("/update-receive")
	public PatientDTO updatePatientReceive(@RequestParam(value = "receiveId") UUID receiveId,
			@RequestParam(value = "patientCode") String patientCode, @RequestParam("patientName") String patientName,
			@RequestParam("phone") String phone,
			@RequestParam("dateOfBirth") @DateTimeFormat(pattern = "dd/MM/yyyy") Date dateOfBirth,
			@RequestParam("gender") Integer gender, @RequestParam("address") String address,
			@RequestParam("ordinalNumber") Short ordinalNumber,
			@RequestParam("clinicalExamPrice") String clinicalExamPrice,
			@RequestParam("roomServiceId") UUID roomServiceId, @RequestParam("staffId") UUID staffId,
			@RequestParam("debt") Long debt, @RequestParam("examinationReason") String examinationReason,
			Principal principal) {
		String username = principal.getName();
		return receivePatientServiceImpl.updateReceivePatient(receiveId, patientCode, patientName, phone, dateOfBirth,
				gender, address, ordinalNumber, clinicalExamPrice, roomServiceId, staffId, debt, examinationReason,
				username, staffId);
	}

	@GetMapping("/get-patient-receive")
	public ReceivePatientPaggingDTO getListPatientReceive(@RequestParam(value = "pageSize") Integer pageSize,
			@RequestParam(value = "pageIndex") Integer pageIndex) {
		return receivePatientServiceImpl.getListPatientReceive(pageSize, pageIndex);
	}

	@GetMapping("/search-receive")
	public ReceivePatientPaggingDTO searchReceive(@RequestParam("roomName") String roomName,
			@RequestParam("patientCode") String patientCode, @RequestParam("patientName") String patientName,
			@RequestParam("phone") String phone, @RequestParam("fullName") String fullName,
			@RequestParam(value = "status", required = false) Integer status,
			@RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date date,
			@RequestParam(value = "pageSize") Integer pageSize, @RequestParam(value = "pageIndex") Integer pageIndex) {
		return receivePatientServiceImpl.searchReceive(roomName, patientCode, patientName, phone, fullName, status,
				date, pageSize, pageIndex);
	}

	@PutMapping("/cancel-receive")
	public Boolean cancelReceive(@RequestParam(value = "id") UUID id) {
		return receivePatientServiceImpl.cancelReceive(id);
	}

	@GetMapping("/check-is-receive")
	public ReceivePatientDTO checkIsReceive(@RequestParam(value = "patientCode", required = false) String patientCode,
			@RequestParam(value = "phone", required = false) String phone) {
		return receivePatientServiceImpl.checkIsReceive(patientCode, phone);
	}
}
