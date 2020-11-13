package com.github.cmateam.cmaserver.dto;

import java.util.Date;
import java.util.UUID;

public class ServiceReportDTO {
	private UUID id;
	private String result;
	private String htmlReport;
	private String note;
	private StaffDTO staff;
	private ServiceDTO service;
	private MedicalExamDTO medicalExamination;
	private Integer status;
	private Date createdAt;
	private Date updatedAt;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getHtmlReport() {
		return htmlReport;
	}

	public void setHtmlReport(String htmlReport) {
		this.htmlReport = htmlReport;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public StaffDTO getStaff() {
		return staff;
	}

	public void setStaff(StaffDTO staff) {
		this.staff = staff;
	}

	public ServiceDTO getService() {
		return service;
	}

	public void setService(ServiceDTO service) {
		this.service = service;
	}

	public MedicalExamDTO getMedicalExamination() {
		return medicalExamination;
	}

	public void setMedicalExamination(MedicalExamDTO medicalExamination) {
		this.medicalExamination = medicalExamination;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
