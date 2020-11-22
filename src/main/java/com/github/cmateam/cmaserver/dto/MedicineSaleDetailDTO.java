package com.github.cmateam.cmaserver.dto;

import java.util.UUID;

public class MedicineSaleDetailDTO {
	private UUID medicineSaleDeatilId;
	private Short quantity;
	private Short quantityTaken;
	private Long amount;
	private Integer type;
	private MedicineSaleDTO medicineSaleByMedicineSaleId;
	private MedicineDTO medicineByMedicineId;
	private String noteMedicineSaleDetail;

	public String getNoteMedicineSaleDetail() {
		return noteMedicineSaleDetail;
	}

	public void setNoteMedicineSaleDetail(String noteMedicineSaleDetail) {
		this.noteMedicineSaleDetail = noteMedicineSaleDetail;
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

	public MedicineSaleDTO getMedicineSaleByMedicineSaleId() {
		return medicineSaleByMedicineSaleId;
	}

	public void setMedicineSaleByMedicineSaleId(MedicineSaleDTO medicineSaleByMedicineSaleId) {
		this.medicineSaleByMedicineSaleId = medicineSaleByMedicineSaleId;
	}

	public MedicineDTO getMedicineByMedicineId() {
		return medicineByMedicineId;
	}

	public void setMedicineByMedicineId(MedicineDTO medicineByMedicineId) {
		this.medicineByMedicineId = medicineByMedicineId;
	}

	public UUID getMedicineSaleDeatilId() {
		return medicineSaleDeatilId;
	}

	public void setMedicineSaleDeatilId(UUID medicineSaleDeatilId) {
		this.medicineSaleDeatilId = medicineSaleDeatilId;
	}

}
