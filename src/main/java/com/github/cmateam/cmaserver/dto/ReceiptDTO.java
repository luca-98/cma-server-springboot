package com.github.cmateam.cmaserver.dto;

import java.util.Date;
import java.util.UUID;

public class ReceiptDTO {
	private UUID receiptId;
	private Long totalAmount;
	private Long amountPaid;
	private Date createdAt;
	private String nameOfReceipt;

	public UUID getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(UUID receiptId) {
		this.receiptId = receiptId;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Long getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(Long amountPaid) {
		this.amountPaid = amountPaid;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getNameOfReceipt() {
		return nameOfReceipt;
	}

	public void setNameOfReceipt(String nameOfReceipt) {
		this.nameOfReceipt = nameOfReceipt;
	}

}
