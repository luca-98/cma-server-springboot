package com.github.cmateam.cmaserver.dto;

public class ReceiptShowDebtAfterDTO {
	private ReceiptDTO receiptDto;
	private Long amountOld;
	private Long amountPaidBeforeTime;
	private Long amountPaidAfterPaiedBefore;

	public ReceiptDTO getReceiptDto() {
		return receiptDto;
	}

	public void setReceiptDto(ReceiptDTO receiptDto) {
		this.receiptDto = receiptDto;
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
