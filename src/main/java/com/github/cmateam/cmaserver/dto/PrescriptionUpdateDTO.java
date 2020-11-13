package com.github.cmateam.cmaserver.dto;

import java.util.List;
import java.util.UUID;

public class PrescriptionUpdateDTO {
	private UUID medicalExamId;
	private UUID patientId;
	private UUID prescriptionId;
	private String patientName;
	private String patientCode;
	private String phone;
	private String dateOfBirth;
	private Integer gender;
	private String address;
	private String note;
	private List<PrescriptionDetailTableDTO> lstMedicineDetail;

	public UUID getMedicalExamId() {
		return medicalExamId;
	}

	public void setMedicalExamId(UUID medicalExamId) {
		this.medicalExamId = medicalExamId;
	}

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<PrescriptionDetailTableDTO> getLstMedicineDetail() {
		return lstMedicineDetail;
	}

	public void setLstMedicineDetail(List<PrescriptionDetailTableDTO> lstMedicineDetail) {
		this.lstMedicineDetail = lstMedicineDetail;
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

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

}
