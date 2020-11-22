package com.github.cmateam.cmaserver.dto;

import java.util.Date;
import java.util.UUID;

public class DebtPaymentSlipDTO {
	private UUID debtPaymentId;
	private Date debtPaymentDate;
	private Long debtPaymentAmount;
	private String debtPaymentNote;
	private PatientDTO patient;
	private StaffDTO staff;
	private String nameOfVoucherType;

	public UUID getDebtPaymentId() {
		return debtPaymentId;
	}

	public void setDebtPaymentId(UUID debtPaymentId) {
		this.debtPaymentId = debtPaymentId;
	}

	public Date getDebtPaymentDate() {
		return debtPaymentDate;
	}

	public void setDebtPaymentDate(Date debtPaymentDate) {
		this.debtPaymentDate = debtPaymentDate;
	}

	public Long getDebtPaymentAmount() {
		return debtPaymentAmount;
	}

	public void setDebtPaymentAmount(Long debtPaymentAmount) {
		this.debtPaymentAmount = debtPaymentAmount;
	}

	public String getDebtPaymentNote() {
		return debtPaymentNote;
	}

	public void setDebtPaymentNote(String debtPaymentNote) {
		this.debtPaymentNote = debtPaymentNote;
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

	public String getNameOfVoucherType() {
		return nameOfVoucherType;
	}

	public void setNameOfVoucherType(String nameOfVoucherType) {
		this.nameOfVoucherType = nameOfVoucherType;
	}

}
