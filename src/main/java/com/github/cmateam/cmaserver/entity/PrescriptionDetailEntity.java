package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;

@Entity
@Table(name = "prescription_detail")
public class PrescriptionDetailEntity extends BaseEntity {
    private Short quantity;
    private String dosage;
    private String note;
    private PrescriptionEntity prescriptionByPrescriptionId;
    private MedicineEntity medicineByMedicineId;
    private Short quantityTaken;
    
    @Basic
    @Column(name = "quantity_taken")
    public Short getQuantityTaken() {
		return quantityTaken;
	}

	public void setQuantityTaken(Short quantityTaken) {
		this.quantityTaken = quantityTaken;
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
    @Column(name = "dosage")
    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    @Basic
    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @ManyToOne
    @JoinColumn(name = "prescription_id", referencedColumnName = "id")
    public PrescriptionEntity getPrescriptionByPrescriptionId() {
        return prescriptionByPrescriptionId;
    }

    public void setPrescriptionByPrescriptionId(PrescriptionEntity prescriptionByPrescriptionId) {
        this.prescriptionByPrescriptionId = prescriptionByPrescriptionId;
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
