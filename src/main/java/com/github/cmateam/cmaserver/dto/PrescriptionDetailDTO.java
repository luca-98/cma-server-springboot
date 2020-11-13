package com.github.cmateam.cmaserver.dto;

import java.util.UUID;

public class PrescriptionDetailDTO {
	private UUID id;
	private Short quantity;
	private String dosage;
	private String noteDetail;
	//private PrescriptionDTO prescriptionByPrescriptionId;
	private MedicineDTO medicineByMedicineId;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Short getQuantity() {
		return quantity;
	}

	public void setQuantity(Short quantity) {
		this.quantity = quantity;
	}

	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	public String getNoteDetail() {
		return noteDetail;
	}

	public void setNoteDetail(String noteDetail) {
		this.noteDetail = noteDetail;
	}

//	public PrescriptionDTO getPrescriptionByPrescriptionId() {
//		return prescriptionByPrescriptionId;
//	}
//
//	public void setPrescriptionByPrescriptionId(PrescriptionDTO prescriptionByPrescriptionId) {
//		this.prescriptionByPrescriptionId = prescriptionByPrescriptionId;
//	}

	public MedicineDTO getMedicineByMedicineId() {
		return medicineByMedicineId;
	}

	public void setMedicineByMedicineId(MedicineDTO medicineByMedicineId) {
		this.medicineByMedicineId = medicineByMedicineId;
	}

}
