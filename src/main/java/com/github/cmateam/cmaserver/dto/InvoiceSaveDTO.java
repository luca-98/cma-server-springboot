package com.github.cmateam.cmaserver.dto;

import java.util.List;
import java.util.UUID;

public class InvoiceSaveDTO {
	private UUID invoiceId;
	private List<InvoiceDetailSaveDTO> lstInvoidDetailsSave;

	public UUID getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(UUID invoiceId) {
		this.invoiceId = invoiceId;
	}

	public List<InvoiceDetailSaveDTO> getLstInvoidDetailsSave() {
		return lstInvoidDetailsSave;
	}

	public void setLstInvoidDetailsSave(List<InvoiceDetailSaveDTO> lstInvoidDetailsSave) {
		this.lstInvoidDetailsSave = lstInvoidDetailsSave;
	}

}
