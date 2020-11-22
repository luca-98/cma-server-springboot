package com.github.cmateam.cmaserver.dto;

import java.util.List;
import java.util.UUID;

public class DebtPaymentSlipSaveDTO {
	private UUID patientId;
	private UUID supplierId;
	private String patientCode;
	private String patientName;
	private String supplierName;
	private String date;
	private Long totalAmountPaymentDebt;
	private String note;
	private String username;
	private List<InvoiceDetailUpdateTO> lstInvoiceDetailUpdateDtos;

	public List<InvoiceDetailUpdateTO> getLstInvoiceDetailUpdateDtos() {
		return lstInvoiceDetailUpdateDtos;
	}

	public void setLstInvoiceDetailUpdateDtos(List<InvoiceDetailUpdateTO> lstInvoiceDetailUpdateDtos) {
		this.lstInvoiceDetailUpdateDtos = lstInvoiceDetailUpdateDtos;
	}

	public UUID getPatientId() {
		return patientId;
	}

	public void setPatientId(UUID patientId) {
		this.patientId = patientId;
	}

	public UUID getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(UUID supplierId) {
		this.supplierId = supplierId;
	}

	public String getPatientCode() {
		return patientCode;
	}

	public void setPatientCode(String patientCode) {
		this.patientCode = patientCode;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
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

}
