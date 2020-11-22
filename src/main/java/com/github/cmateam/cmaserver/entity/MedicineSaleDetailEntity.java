package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;

@Entity
@Table(name = "medicine_sale_detail", schema = "cma", catalog = "postgres")
public class MedicineSaleDetailEntity extends BaseEntity {
	private Short quantity;
	private Short quantityTaken;
	private Long amount;
	private Integer type;
	private MedicineSaleEntity medicineSaleByMedicineSaleId;
	private MedicineEntity medicineByMedicineId;
	private String note;

	@Basic
	@Column(name = "note")
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Basic
	@Column(name = "quantity")
	public Short getQuantity() {
		return quantity;
	}

	public void setQuantity(Short quantity) {
		this.quantity = quantity;
	}

	@Basic
	@Column(name = "quantity_taken")
	public Short getQuantityTaken() {
		return quantityTaken;
	}

	public void setQuantityTaken(Short quantityTaken) {
		this.quantityTaken = quantityTaken;
	}

	@Basic
	@Column(name = "amount")
	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	@Basic
	@Column(name = "type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@ManyToOne
	@JoinColumn(name = "medicine_sale_id", referencedColumnName = "id")
	public MedicineSaleEntity getMedicineSaleByMedicineSaleId() {
		return medicineSaleByMedicineSaleId;
	}

	public void setMedicineSaleByMedicineSaleId(MedicineSaleEntity medicineSaleByMedicineSaleId) {
		this.medicineSaleByMedicineSaleId = medicineSaleByMedicineSaleId;
	}

	@ManyToOne
	@JoinColumn(name = "medicine_id", referencedColumnName = "id")
	public MedicineEntity getMedicineByMedicineId() {
		return medicineByMedicineId;
	}

	public void setMedicineByMedicineId(MedicineEntity medicineByMedicineId) {
		this.medicineByMedicineId = medicineByMedicineId;
	}
}
