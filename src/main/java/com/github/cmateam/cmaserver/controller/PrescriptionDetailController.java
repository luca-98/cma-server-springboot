package com.github.cmateam.cmaserver.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.cmateam.cmaserver.service.PrescriptionDetailServiceImpl;

@RestController
@RequestMapping("/prescription-detail")
public class PrescriptionDetailController {
	private PrescriptionDetailServiceImpl prescriptionDetailServiceImpl;

	@Autowired
	public PrescriptionDetailController(PrescriptionDetailServiceImpl prescriptionDetailServiceImpl) {
		this.prescriptionDetailServiceImpl = prescriptionDetailServiceImpl;
	}

	@DeleteMapping("/delete-prescription")
	public Boolean delete(@RequestParam("prescriptionDetailId") UUID prescriptionDetailId) {
		return prescriptionDetailServiceImpl.deleteAPrescriptionDetail(prescriptionDetailId);
	}

}
