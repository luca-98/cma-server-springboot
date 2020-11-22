package com.github.cmateam.cmaserver.dto;

import java.util.UUID;

public class InvoiceDetailUpdateTO {
	private UUID invoiceDetailId;
	private Long amountCurrentPaid;

	public UUID getInvoiceDetailId() {
		return invoiceDetailId;
	}

	public void setInvoiceDetailId(UUID invoiceDetailId) {
		this.invoiceDetailId = invoiceDetailId;
	}

	public Long getAmountCurrentPaid() {
		return amountCurrentPaid;
	}

	public void setAmountCurrentPaid(Long amountCurrentPaid) {
		this.amountCurrentPaid = amountCurrentPaid;
	}

}
