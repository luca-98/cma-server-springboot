package com.github.cmateam.cmaserver.dto;

import java.util.List;
import java.util.UUID;

public class ReceiptSaveDTO {
	private UUID supplierId;
	private UUID receiptId;
	private Long totalAmount;
	private Long amountPaid;
	private List<ReceiptDetailTableDTO> lstReceiptDetails;
	private String supplierName;
	private String address;
	private String phone;
	private String email;
	private String accountNumber;
	private Long debt;
	private String username;

	public UUID getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(UUID supplierId) {
		this.supplierId = supplierId;
	}

	public UUID getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(UUID receiptId) {
		this.receiptId = receiptId;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Long getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(Long amountPaid) {
		this.amountPaid = amountPaid;
	}

	public List<ReceiptDetailTableDTO> getLstReceiptDetails() {
		return lstReceiptDetails;
	}

	public void setLstReceiptDetails(List<ReceiptDetailTableDTO> lstReceiptDetails) {
		this.lstReceiptDetails = lstReceiptDetails;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Long getDebt() {
		return debt;
	}

	public void setDebt(Long debt) {
		this.debt = debt;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
