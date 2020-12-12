package com.github.cmateam.cmaserver.dto;

import java.util.List;

public class InvoiceSaveListDTO {
	private List<InvoiceSaveDTO> lstInvoidDetailListInvoiceSave;

	public List<InvoiceSaveDTO> getLstInvoidDetailListInvoiceSave() {
		return lstInvoidDetailListInvoiceSave;
	}

	public void setLstInvoidDetailListInvoiceSave(List<InvoiceSaveDTO> lstInvoidDetailListInvoiceSave) {
		this.lstInvoidDetailListInvoiceSave = lstInvoidDetailListInvoiceSave;
	}
	
}
