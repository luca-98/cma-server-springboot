package com.github.cmateam.cmaserver.dto;

import java.util.UUID;

public class MedicineSaleTableDTO {
	private UUID medicineId;
	private UUID medicineSaleDetailId;
	private Short quantity;
	private Short quantityTaken;
	private Long amount;
	private Integer type;
	private String noteMedicineSaleDetail;

	public String getNoteMedicineSaleDetail() {
		return noteMedicineSaleDetail;
	}

	public void setNoteMedicineSaleDetail(String noteMedicineSaleDetail) {
		this.noteMedicineSaleDetail = noteMedicineSaleDetail;
	}

	public UUID getMedicineId() {
		return medicineId;
	}

	public void setMedicineId(UUID medicineId) {
		this.medicineId = medicineId;
	}

	public Short getQuantity() {
		return quantity;
	}

	public void setQuantity(Short quantity) {
		this.quantity = quantity;
	}

	public Short getQuantityTaken() {
		return quantityTaken;
	}

	public void setQuantityTaken(Short quantityTaken) {
		this.quantityTaken = quantityTaken;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public UUID getMedicineSaleDetailId() {
		return medicineSaleDetailId;
	}

	public void setMedicineSaleDetailId(UUID medicineSaleDetailId) {
		this.medicineSaleDetailId = medicineSaleDetailId;
	}

}
