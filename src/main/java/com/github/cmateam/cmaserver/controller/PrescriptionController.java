package com.github.cmateam.cmaserver.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.cmateam.cmaserver.dto.PrescriptionDTO;
import com.github.cmateam.cmaserver.dto.PrescriptionUpdateDTO;
import com.github.cmateam.cmaserver.service.PrescriptionServiceImpl;

@RestController
@RequestMapping("/prescription")
public class PrescriptionController {
	private PrescriptionServiceImpl prescriptionServiceImpl;

	@Autowired
	public PrescriptionController(PrescriptionServiceImpl prescriptionServiceImpl) {
		this.prescriptionServiceImpl = prescriptionServiceImpl;
	}

	@PostMapping("/update-prescription")
	public UUID updatePrescription(@RequestBody PrescriptionUpdateDTO prescriptionUpdateDTO) {
		return prescriptionServiceImpl.updatePrescription(prescriptionUpdateDTO);
	}

	@GetMapping("/get-prescription-by-medicalexamId")
	public PrescriptionDTO getPrescriptionByMedicalExamId(@RequestParam("medicalExamId") UUID medicalExamId) {
		return prescriptionServiceImpl.getPrescriptionByMedicalExamId(medicalExamId);
	}

	@PutMapping("/update-html-report")
	public Boolean updateHtmlReport(@RequestParam UUID id, @RequestParam String htmlReport) {
		return prescriptionServiceImpl.updateHtmlReport(id, htmlReport);
	}

	@GetMapping(value = "{id}")
	public ResponseEntity<?> getOneMedicalExam(@PathVariable("id") UUID id) {
		return prescriptionServiceImpl.getOne(id);
	}
}
