package com.github.cmateam.cmaserver.dto;

import java.util.Date;
import java.util.UUID;

public class PaymentVoucherDTO {
	private UUID paymentVoucherId;
	private Date paymentVoucherDate;
	private Long paymentVoucherAmount;
	private String paymentVoucherDescription;
	private String nameOfVoucherType;
	private Long voucherNumber;
	private String objectPayment;
	private StaffDTO staff;

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

	public String getObjectPayment() {
		return objectPayment;
	}

	public void setObjectPayment(String objectPayment) {
		this.objectPayment = objectPayment;
	}

	public String getNameOfVoucherType() {
		return nameOfVoucherType;
	}

	public void setNameOfVoucherType(String nameOfVoucherType) {
		this.nameOfVoucherType = nameOfVoucherType;
	}

	public StaffDTO getStaff() {
		return staff;
	}

	public void setStaff(StaffDTO staff) {
		this.staff = staff;
	}

	public Long getVoucherNumber() {
		return voucherNumber;
	}

	public void setVoucherNumber(Long voucherNumber) {
		this.voucherNumber = voucherNumber;
	}

}
