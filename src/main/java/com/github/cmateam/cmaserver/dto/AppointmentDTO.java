package com.github.cmateam.cmaserver.dto;

import java.util.Date;
import java.util.UUID;

public class AppointmentDTO {
	private UUID id;
	private Date dayOfExamination;
	private String time;
	private OrdinalNumberDTO ordinalNumber;
	private PatientDTO patient;
	private StaffDTO staff;
	private Integer status;
	private Boolean timeExist;
	private Boolean appointmentDateExist;

	public Date getDayOfExamination() {
		return dayOfExamination;
	}

	public void setDayOfExamination(Date dayOfExamination) {
		this.dayOfExamination = dayOfExamination;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public OrdinalNumberDTO getOrdinalNumber() {
		return ordinalNumber;
	}

	public void setOrdinalNumber(OrdinalNumberDTO ordinalNumber) {
		this.ordinalNumber = ordinalNumber;
	}

	public PatientDTO getPatient() {
		return patient;
	}

	public void setPatient(PatientDTO patient) {
		this.patient = patient;
	}

	public StaffDTO getStaff() {
		return staff;
	}

	public void setStaff(StaffDTO staff) {
		this.staff = staff;
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

	public Boolean getTimeExist() {
		return timeExist;
	}

	public void setTimeExist(Boolean timeExist) {
		this.timeExist = timeExist;
	}

	public Boolean getAppointmentDateExist() {
		return appointmentDateExist;
	}

	public void setAppointmentDateExist(Boolean appointmentDateExist) {
		this.appointmentDateExist = appointmentDateExist;
	}

}
