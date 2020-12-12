package com.github.cmateam.cmaserver.dto;

import java.util.List;
import java.util.UUID;

public class DebtPaymentSlipSupplierSaveDTO {
	private UUID supplierId;
	private String date;
	private Long totalAmountPaymentDebt;
	private String note;
	private String username;
	private Long voucherNumber;
	List<ReceiptUpdateDTO> lstReceiptUpdates;

	public UUID getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(UUID supplierId) {
		this.supplierId = supplierId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Long getTotalAmountPaymentDebt() {
		return totalAmountPaymentDebt;
	}

	public void setTotalAmountPaymentDebt(Long totalAmountPaymentDebt) {
		this.totalAmountPaymentDebt = totalAmountPaymentDebt;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<ReceiptUpdateDTO> getLstReceiptUpdates() {
		return lstReceiptUpdates;
	}

	public void setLstReceiptUpdates(List<ReceiptUpdateDTO> lstReceiptUpdates) {
		this.lstReceiptUpdates = lstReceiptUpdates;
	}

	public Long getVoucherNumber() {
		return voucherNumber;
	}

	public void setVoucherNumber(Long voucherNumber) {
		this.voucherNumber = voucherNumber;
	}

}
