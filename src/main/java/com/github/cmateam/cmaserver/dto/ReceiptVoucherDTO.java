package com.github.cmateam.cmaserver.dto;

import java.util.Date;
import java.util.UUID;

public class ReceiptVoucherDTO {
	private UUID receiptVoucherId;
	private Date receiptVoucherDate;
	private Long receiptVoucherAmount;
	private String receiptVoucherDescription;
	private PatientDTO patient;
	private StaffDTO staff;
	private String nameOfVoucherType;

	public UUID getReceiptVoucherId() {
		return receiptVoucherId;
	}

	public void setReceiptVoucherId(UUID receiptVoucherId) {
		this.receiptVoucherId = receiptVoucherId;
	}

	public Date getReceiptVoucherDate() {
		return receiptVoucherDate;
	}

	public void setReceiptVoucherDate(Date receiptVoucherDate) {
		this.receiptVoucherDate = receiptVoucherDate;
	}

	public Long getReceiptVoucherAmount() {
		return receiptVoucherAmount;
	}

	public void setReceiptVoucherAmount(Long receiptVoucherAmount) {
		this.receiptVoucherAmount = receiptVoucherAmount;
	}

	public String getReceiptVoucherDescription() {
		return receiptVoucherDescription;
	}

	public void setReceiptVoucherDescription(String receiptVoucherDescription) {
		this.receiptVoucherDescription = receiptVoucherDescription;
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
