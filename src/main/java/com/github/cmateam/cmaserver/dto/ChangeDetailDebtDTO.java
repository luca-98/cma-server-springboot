package com.github.cmateam.cmaserver.dto;

import java.util.UUID;

public class ChangeDetailDebtDTO {
	private UUID invoiceDetailId;
	private UUID receiptId;
	private Long amountOld;
	private Long amountPaidAfterPaiedBefore;
	private Long amountPaidBeforeTime;

	public UUID getInvoiceDetailId() {
		return invoiceDetailId;
	}

	public void setInvoiceDetailId(UUID invoiceDetailId) {
		this.invoiceDetailId = invoiceDetailId;
	}

	public UUID getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(UUID receiptId) {
		this.receiptId = receiptId;
	}

	public Long getAmountOld() {
		return amountOld;
	}

	public void setAmountOld(Long amountOld) {
		this.amountOld = amountOld;
	}

	public Long getAmountPaidBeforeTime() {
		return amountPaidBeforeTime;
	}

	public void setAmountPaidBeforeTime(Long amountPaidBeforeTime) {
		this.amountPaidBeforeTime = amountPaidBeforeTime;
	}

	public Long getAmountPaidAfterPaiedBefore() {
		return amountPaidAfterPaiedBefore;
	}

	public void setAmountPaidAfterPaiedBefore(Long amountPaidAfterPaiedBefore) {
		this.amountPaidAfterPaiedBefore = amountPaidAfterPaiedBefore;
	}

}
