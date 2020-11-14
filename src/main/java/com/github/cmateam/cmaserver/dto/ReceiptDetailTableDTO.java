package com.github.cmateam.cmaserver.dto;

import java.util.UUID;

public class ReceiptDetailTableDTO {
	private Short quantityDetail;
	private UUID medicineId;
	private UUID materialId;
	private Long amountDetail;
	private UUID groupMedicineId;
	private UUID groupMaterialId;
	private String receiptName;
	private Long price;
	private String unitName;

	public Short getQuantityDetail() {
		return quantityDetail;
	}

	public void setQuantityDetail(Short quantityDetail) {
		this.quantityDetail = quantityDetail;
	}

	public UUID getMedicineId() {
		return medicineId;
	}

	public void setMedicineId(UUID medicineId) {
		this.medicineId = medicineId;
	}

	public UUID getMaterialId() {
		return materialId;
	}

	public void setMaterialId(UUID materialId) {
		this.materialId = materialId;
	}

	public Long getAmountDetail() {
		return amountDetail;
	}

	public void setAmountDetail(Long amountDetail) {
		this.amountDetail = amountDetail;
	}

	public UUID getGroupMedicineId() {
		return groupMedicineId;
	}

	public void setGroupMedicineId(UUID groupMedicineId) {
		this.groupMedicineId = groupMedicineId;
	}

	public UUID getGroupMaterialId() {
		return groupMaterialId;
	}

	public void setGroupMaterialId(UUID groupMaterialId) {
		this.groupMaterialId = groupMaterialId;
	}

	public String getReceiptName() {
		return receiptName;
	}

	public void setReceiptName(String receiptName) {
		this.receiptName = receiptName;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

}
