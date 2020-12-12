package com.github.cmateam.cmaserver.dto;

public class InvoiceDetailShowDebtAfterDTO {
	private InvoiceDetailDTO invoiceDetailAfter;
	private Long amountOld;
	private Long amountPaidBeforeTime;
	private Long amountPaidAfterPaiedBefore;

	public InvoiceDetailDTO getInvoiceDetailAfter() {
		return invoiceDetailAfter;
	}

	public void setInvoiceDetailAfter(InvoiceDetailDTO invoiceDetailAfter) {
		this.invoiceDetailAfter = invoiceDetailAfter;
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
