package com.github.cmateam.cmaserver.dto;

import java.util.List;
import java.util.UUID;

public class SubclinicalDTO {
	private UUID patientId;
	private UUID medicaleExamId;
	private List<ServiceReportPostDTO> serviceReportPostDTOs;

	public UUID getPatientId() {
		return patientId;
	}

	public void setPatientId(UUID patientId) {
		this.patientId = patientId;
	}

	public UUID getMedicaleExamId() {
		return medicaleExamId;
	}

	public void setMedicaleExamId(UUID medicaleExamId) {
		this.medicaleExamId = medicaleExamId;
	}

	public List<ServiceReportPostDTO> getServiceReportPostDTOs() {
		return serviceReportPostDTOs;
	}

	public void setServiceReportPostDTOs(List<ServiceReportPostDTO> serviceReportPostDTOs) {
		this.serviceReportPostDTOs = serviceReportPostDTOs;
	}

}
