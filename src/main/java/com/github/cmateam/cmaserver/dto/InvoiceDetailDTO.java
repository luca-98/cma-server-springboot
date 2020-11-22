package com.github.cmateam.cmaserver.dto;

import java.util.Date;
import java.util.UUID;

public class InvoiceDetailDTO {
	private UUID invoiceDetailId;
	private Short quantity;
	private Long amount;
	private Long amountPaid;
	private ServiceDTO serviceDto;
	private MedicineSaleDTO medicineSaleDto;
	private Date createdAt;

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public UUID getInvoiceDetailId() {
		return invoiceDetailId;
	}

	public void setInvoiceDetailId(UUID invoiceDetailId) {
		this.invoiceDetailId = invoiceDetailId;
	}

	public Short getQuantity() {
		return quantity;
	}

	public void setQuantity(Short quantity) {
		this.quantity = quantity;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(Long amountPaid) {
		this.amountPaid = amountPaid;
	}

	public ServiceDTO getServiceDto() {
		return serviceDto;
	}

	public void setServiceDto(ServiceDTO serviceDto) {
		this.serviceDto = serviceDto;
	}

	public MedicineSaleDTO getMedicineSaleDto() {
		return medicineSaleDto;
	}

	public void setMedicineSaleDto(MedicineSaleDTO medicineSaleDto) {
		this.medicineSaleDto = medicineSaleDto;
	}

}
