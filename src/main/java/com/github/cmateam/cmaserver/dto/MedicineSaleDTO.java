package com.github.cmateam.cmaserver.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MedicineSaleDTO {
	private UUID id;
	private StaffDTO staffEntity;
	private PatientDTO patientEntity;
	private PrescriptionDTO prescriptionEntity;
	private Long totalAmout;
	private List<MedicineSaleDetailDTO> lstMedicineSaleDetailDTO;
	private Date createdAt;
	private Date updatedAt;
	private String nameMedicineSale;

	public String getNameMedicineSale() {
		return nameMedicineSale;
	}

	public void setNameMedicineSale(String nameMedicineSale) {
		this.nameMedicineSale = nameMedicineSale;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public StaffDTO getStaffEntity() {
		return staffEntity;
	}

	public void setStaffEntity(StaffDTO staffEntity) {
		this.staffEntity = staffEntity;
	}

	public PrescriptionDTO getPrescriptionEntity() {
		return prescriptionEntity;
	}

	public void setPrescriptionEntity(PrescriptionDTO prescriptionEntity) {
		this.prescriptionEntity = prescriptionEntity;
	}

	public Long getTotalAmout() {
		return totalAmout;
	}

	public void setTotalAmout(Long totalAmout) {
		this.totalAmout = totalAmout;
	}

	public List<MedicineSaleDetailDTO> getLstMedicineSaleDetailDTO() {
		return lstMedicineSaleDetailDTO;
	}

	public void setLstMedicineSaleDetailDTO(List<MedicineSaleDetailDTO> lstMedicineSaleDetailDTO) {
		this.lstMedicineSaleDetailDTO = lstMedicineSaleDetailDTO;
	}

	public PatientDTO getPatientEntity() {
		return patientEntity;
	}

	public void setPatientEntity(PatientDTO patientEntity) {
		this.patientEntity = patientEntity;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}
