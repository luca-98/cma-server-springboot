package com.github.cmateam.cmaserver.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DebtPaymentSlipDTO {
	private UUID debtPaymentId;
	private Date debtPaymentDate;
	private Long debtPaymentAmount;
	private String debtPaymentNote;
	private PatientDTO patient;
	private SupplierDTO supplier;
	private StaffDTO staff;
	private String nameOfVoucherType;
	private Long voucherNumber;
	private List<ChangeDetailDebtDTO> lstChangeDetailDebtDTO;
	private List<InvoiceDetailShowDebtAfterDTO> lstInvoiceDetailShowDebtAfters;
	private List<ReceiptShowDebtAfterDTO> lstReceiptShowDebtAfters;

	public UUID getDebtPaymentId() {
		return debtPaymentId;
	}

	public void setDebtPaymentId(UUID debtPaymentId) {
		this.debtPaymentId = debtPaymentId;
	}

	public Date getDebtPaymentDate() {
		return debtPaymentDate;
	}

	public void setDebtPaymentDate(Date debtPaymentDate) {
		this.debtPaymentDate = debtPaymentDate;
	}

	public Long getDebtPaymentAmount() {
		return debtPaymentAmount;
	}

	public void setDebtPaymentAmount(Long debtPaymentAmount) {
		this.debtPaymentAmount = debtPaymentAmount;
	}

	public String getDebtPaymentNote() {
		return debtPaymentNote;
	}

	public void setDebtPaymentNote(String debtPaymentNote) {
		this.debtPaymentNote = debtPaymentNote;
	}

	public PatientDTO getPatient() {
		return patient;
	}

	public void setPatient(PatientDTO patient) {
		this.patient = patient;
	}

	public StaffDTO getStaff() {
		return staff;
	}

	public void setStaff(StaffDTO staff) {
		this.staff = staff;
	}

	public String getNameOfVoucherType() {
		return nameOfVoucherType;
	}

	public void setNameOfVoucherType(String nameOfVoucherType) {
		this.nameOfVoucherType = nameOfVoucherType;
	}

	public SupplierDTO getSupplier() {
		return supplier;
	}

	public void setSupplier(SupplierDTO supplier) {
		this.supplier = supplier;
	}

	public List<ChangeDetailDebtDTO> getLstChangeDetailDebtDTO() {
		return lstChangeDetailDebtDTO;
	}

	public void setLstChangeDetailDebtDTO(List<ChangeDetailDebtDTO> lstChangeDetailDebtDTO) {
		this.lstChangeDetailDebtDTO = lstChangeDetailDebtDTO;
	}

	public List<InvoiceDetailShowDebtAfterDTO> getLstInvoiceDetailShowDebtAfters() {
		return lstInvoiceDetailShowDebtAfters;
	}

	public void setLstInvoiceDetailShowDebtAfters(List<InvoiceDetailShowDebtAfterDTO> lstInvoiceDetailShowDebtAfters) {
		this.lstInvoiceDetailShowDebtAfters = lstInvoiceDetailShowDebtAfters;
	}

	public List<ReceiptShowDebtAfterDTO> getLstReceiptShowDebtAfters() {
		return lstReceiptShowDebtAfters;
	}

	public void setLstReceiptShowDebtAfters(List<ReceiptShowDebtAfterDTO> lstReceiptShowDebtAfters) {
		this.lstReceiptShowDebtAfters = lstReceiptShowDebtAfters;
	}

	public Long getVoucherNumber() {
		return voucherNumber;
	}

	public void setVoucherNumber(Long voucherNumber) {
		this.voucherNumber = voucherNumber;
	}

}
