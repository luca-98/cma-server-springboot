package com.github.cmateam.cmaserver.dto;

import java.util.Date;
import java.util.UUID;

public class PaymentVoucherDTO {
	private UUID paymentVoucherId;
	private Date paymentVoucherDate;
	private Long paymentVoucherAmount;
	private String paymentVoucherDescription;
	private PatientDTO patient;
	private StaffDTO staff;
	private String nameOfVoucherType;

	public UUID getPaymentVoucherId() {
		return paymentVoucherId;
	}

	public void setPaymentVoucherId(UUID paymentVoucherId) {
		this.paymentVoucherId = paymentVoucherId;
	}

	public Date getPaymentVoucherDate() {
		return paymentVoucherDate;
	}

	public void setPaymentVoucherDate(Date paymentVoucherDate) {
		this.paymentVoucherDate = paymentVoucherDate;
	}

	public Long getPaymentVoucherAmount() {
		return paymentVoucherAmount;
	}

	public void setPaymentVoucherAmount(Long paymentVoucherAmount) {
		this.paymentVoucherAmount = paymentVoucherAmount;
	}

	public String getPaymentVoucherDescription() {
		return paymentVoucherDescription;
	}

	public void setPaymentVoucherDescription(String paymentVoucherDescription) {
		this.paymentVoucherDescription = paymentVoucherDescription;
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
