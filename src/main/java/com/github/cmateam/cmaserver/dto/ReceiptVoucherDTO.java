package com.github.cmateam.cmaserver.dto;

import java.util.Date;
import java.util.UUID;

public class ReceiptVoucherDTO {
	private UUID receiptVoucherId;
	private Date receiptVoucherDate;
	private Long receiptVoucherAmount;
	private Long voucherNumber;
	private String receiptVoucherDescription;
	private StaffDTO staff;
	private String nameOfVoucherType;
	private String objectReceipt;

	public String getObjectReceipt() {
		return objectReceipt;
	}

	public void setObjectReceipt(String objectReceipt) {
		this.objectReceipt = objectReceipt;
	}

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

	public Long getVoucherNumber() {
		return voucherNumber;
	}

	public void setVoucherNumber(Long voucherNumber) {
		this.voucherNumber = voucherNumber;
	}

}
