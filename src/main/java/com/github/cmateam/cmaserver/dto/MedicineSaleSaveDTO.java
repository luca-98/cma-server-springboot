package com.github.cmateam.cmaserver.dto;

import java.util.List;
import java.util.UUID;

public class MedicineSaleSaveDTO {
	private UUID patientId;
	private String patientName;
	private String patientCode;
	private Long totalAmout;
	private UUID prescriptionId;
	private UUID medicineSaleId;
	private String username;
	List<MedicineSaleTableDTO> lstMedicineSaleDetials;

	public UUID getPatientId() {
		return patientId;
	}

	public void setPatientId(UUID patientId) {
		this.patientId = patientId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getPatientCode() {
		return patientCode;
	}

	public void setPatientCode(String patientCode) {
		this.patientCode = patientCode;
	}

	public UUID getPrescriptionId() {
		return prescriptionId;
	}

	public void setPrescriptionId(UUID prescriptionId) {
		this.prescriptionId = prescriptionId;
	}

	public List<MedicineSaleTableDTO> getLstMedicineSaleDetials() {
		return lstMedicineSaleDetials;
	}

	public void setLstMedicineSaleDetials(List<MedicineSaleTableDTO> lstMedicineSaleDetials) {
		this.lstMedicineSaleDetials = lstMedicineSaleDetials;
	}

	public UUID getMedicineSaleId() {
		return medicineSaleId;
	}

	public void setMedicineSaleId(UUID medicineSaleId) {
		this.medicineSaleId = medicineSaleId;
	}

	public Long getTotalAmout() {
		return totalAmout;
	}

	public void setTotalAmout(Long totalAmout) {
		this.totalAmout = totalAmout;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
