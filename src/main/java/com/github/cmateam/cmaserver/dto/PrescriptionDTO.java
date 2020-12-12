package com.github.cmateam.cmaserver.dto;

import java.util.List;
import java.util.UUID;

public class PrescriptionDTO {
	private String note;
	private UUID id;
	private StaffDTO staffByStaffId;
	private MedicalExamDTO medicalExaminationByMedicalExaminationId;
	private List<PrescriptionDetailDTO> lstPrescriptionDetailDTO;
	private String nameOfPrescription;
	private String printDataHtml;

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public StaffDTO getStaffByStaffId() {
		return staffByStaffId;
	}

	public void setStaffByStaffId(StaffDTO staffByStaffId) {
		this.staffByStaffId = staffByStaffId;
	}

	public MedicalExamDTO getMedicalExaminationByMedicalExaminationId() {
		return medicalExaminationByMedicalExaminationId;
	}

	public void setMedicalExaminationByMedicalExaminationId(MedicalExamDTO medicalExaminationByMedicalExaminationId) {
		this.medicalExaminationByMedicalExaminationId = medicalExaminationByMedicalExaminationId;
	}

	public List<PrescriptionDetailDTO> getLstPrescriptionDetailDTO() {
		return lstPrescriptionDetailDTO;
	}

	public void setLstPrescriptionDetailDTO(List<PrescriptionDetailDTO> lstPrescriptionDetailDTO) {
		this.lstPrescriptionDetailDTO = lstPrescriptionDetailDTO;
	}

	public String getNameOfPrescription() {
		return nameOfPrescription;
	}

	public void setNameOfPrescription(String nameOfPrescription) {
		this.nameOfPrescription = nameOfPrescription;
	}

	public String getPrintDataHtml() {
		return this.printDataHtml;
	}

	public void setPrintDataHtml(String printDataHtml) {
		this.printDataHtml = printDataHtml;
	}

}
