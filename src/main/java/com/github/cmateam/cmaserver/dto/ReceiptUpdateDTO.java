package com.github.cmateam.cmaserver.dto;

import java.util.UUID;

public class ReceiptUpdateDTO {
	private UUID receiptId;
	private Long amountCurrentPaid;

	public UUID getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(UUID receiptId) {
		this.receiptId = receiptId;
	}

	public Long getAmountCurrentPaid() {
		return amountCurrentPaid;
	}

	public void setAmountCurrentPaid(Long amountCurrentPaid) {
		this.amountCurrentPaid = amountCurrentPaid;
	}

}
