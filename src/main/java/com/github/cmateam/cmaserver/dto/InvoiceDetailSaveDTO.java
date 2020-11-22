package com.github.cmateam.cmaserver.dto;

import java.util.UUID;

public class InvoiceDetailSaveDTO {
	private UUID invoiceDetailId;
	private Long amountInvoiceDetail;
	private Long amountPaidInvoiceDetail;

	public UUID getInvoiceDetailId() {
		return invoiceDetailId;
	}

	public void setInvoiceDetailId(UUID invoiceDetailId) {
		this.invoiceDetailId = invoiceDetailId;
	}

	public Long getAmountInvoiceDetail() {
		return amountInvoiceDetail;
	}

	public void setAmountInvoiceDetail(Long amountInvoiceDetail) {
		this.amountInvoiceDetail = amountInvoiceDetail;
	}

	public Long getAmountPaidInvoiceDetail() {
		return amountPaidInvoiceDetail;
	}

	public void setAmountPaidInvoiceDetail(Long amountPaidInvoiceDetail) {
		this.amountPaidInvoiceDetail = amountPaidInvoiceDetail;
	}

}
