package com.github.cmateam.cmaserver.dto;

public class PaymentVoucherSaveDTO {
	private String date;
	private Long amount;
	private String description;
	private String username;
	private String objectPayment;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getObjectPayment() {
		return objectPayment;
	}

	public void setObjectPayment(String objectPayment) {
		this.objectPayment = objectPayment;
	}

}
