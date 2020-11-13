package com.github.cmateam.cmaserver.controller;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.dto.MedicalExamTableDTO;
import com.github.cmateam.cmaserver.dto.MedicalExamDTO;
import com.github.cmateam.cmaserver.dto.MedicalExamPaggingDTO;
import com.github.cmateam.cmaserver.service.MedicalExamServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medical-examination")
public class MedicalExamController {

	private MedicalExamServiceImpl medicalExamServiceImpl;

	@Autowired
	public MedicalExamController(MedicalExamServiceImpl medicalExamServiceImpl) {
		this.medicalExamServiceImpl = medicalExamServiceImpl;
	}

	@GetMapping("get-list-medical-exam")
	public MedicalExamPaggingDTO getListMedicalExam(
			@RequestParam("fromDate") @DateTimeFormat(pattern = "dd/MM/yyyy") Date fromDate,
			@RequestParam("toDate") @DateTimeFormat(pattern = "dd/MM/yyyy") Date toDate,
			@RequestParam("roomId") UUID roomId, @RequestParam("doctorId") UUID doctorId,
			@RequestParam("status") Integer status, @RequestParam("clinicalExamCode") String clinicalExamCode,
			@RequestParam("patientCode") String patientCode, @RequestParam("phone") String phone,
			@RequestParam("pageIndex") Integer pageIndex, @RequestParam("pageSize") Integer pageSize) {
		return medicalExamServiceImpl.getListMedicalExam(fromDate, toDate, roomId, doctorId, status, clinicalExamCode,
				patientCode, phone, pageIndex, pageSize);
	}

	@GetMapping("get-next-ordinal-room")
	private Short getNextOrdinalRoom(@RequestParam("roomId") UUID roomId) {
		return medicalExamServiceImpl.getNextOrdinalRoom(roomId);
	}

	@GetMapping("get-next-ordinal-staff")
	private Short getNextOrdinalStaffStaff(@RequestParam("staffId") UUID staffId) {
		return medicalExamServiceImpl.getNextOrdinalStaff(staffId);
	}

	@GetMapping("/search-by-medical-exam-code")
	public List<MedicalExamTableDTO> searchMedicalExamCode(@RequestParam("medicalExamCode") String medicalExamCode) {
		return medicalExamServiceImpl.searchMedicalExamCode(medicalExamCode);
	}

	@GetMapping("/check-medical-exam-exist-by-patient-code")
	public MedicalExamDTO checkMedicalExamByPatientCode(@RequestParam("patientCode") String patientCode) {
		return medicalExamServiceImpl.checkMedicalExamByPatientCode(patientCode);
	}

	// @GetMapping("/check-medical-exam-exist-by-phone")
	// public MedicalExamDTO checkMedicalExamByPhone(@RequestParam("phone") String phone) {
	// 	return medicalExamServiceImpl.checkMedicalExamByPhone(phone);
	// }

	@PutMapping("/change-status")
	public Boolean changeStatus(@RequestParam("id") UUID id, @RequestParam("status") Integer status) {
		return medicalExamServiceImpl.changeStatus(id, status);
	}

	@PutMapping("/change-doctor")
	public Boolean changeDoctor(@RequestParam("id") UUID id, @RequestParam("doctorId") UUID doctorId) {
		return medicalExamServiceImpl.changeDoctor(id, doctorId);
	}

	@GetMapping("/get-medical-exam")
	public MedicalExamDTO getMedicalExam(@RequestParam("id") UUID id) {
		return medicalExamServiceImpl.getMedicalExam(id);
	}

	@PostMapping("/update-medical-exam")
	public MedicalExamDTO createMedicalExam(@RequestParam(value = "medicalExamId", required = false) UUID medicalExamId,
			@RequestParam(value = "patientCode", required = false) String patientCode,
			@RequestParam("patientName") String patientName, @RequestParam("phone") String phone,
			@RequestParam("dateOfBirth") @DateTimeFormat(pattern = "dd/MM/yyyy") Date dateOfBirth,
			@RequestParam("gender") Integer gender, @RequestParam("address") String address,
			@RequestParam("debt") Long debt, @RequestParam("examinationReason") String examinationReason,
			@RequestParam("bloodVessel") String bloodVessel, @RequestParam("bloodPressure") String bloodPressure,
			@RequestParam("breathing") String breathing, @RequestParam("temperature") String temperature,
			@RequestParam("height") String height, @RequestParam("weight") String weight,
			@RequestParam("symptom") String symptom, @RequestParam("summary") String summary,
			@RequestParam("mainDisease") String mainDisease, @RequestParam("mainDiseaseCode") String mainDiseaseCode,
			@RequestParam("extraDisease") String extraDisease,
			@RequestParam("extraDiseaseCode") String extraDiseaseCode, Principal principal) {
		String username = principal.getName();
		return medicalExamServiceImpl.updateMedicalExam(medicalExamId, patientCode, patientName, phone, dateOfBirth,
				gender, address, debt, examinationReason, bloodVessel, bloodPressure, breathing, temperature, height,
				weight, symptom, summary, mainDisease, mainDiseaseCode, extraDisease, extraDiseaseCode, username);
	}
}
