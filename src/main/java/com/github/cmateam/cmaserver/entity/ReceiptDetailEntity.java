package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;

@Entity
@Table(name = "receipt_detail", schema = "cma", catalog = "postgres")
public class ReceiptDetailEntity extends BaseEntity {
    private Short quantity;
    private Long amount;
    private ReceiptEntity receiptByReceiptId;
    private MedicineEntity medicineByMedicineId;
    private MaterialEntity materialByMaterialId;

    @Basic
    @Column(name = "quantity")
    public Short getQuantity() {
        return quantity;
    }

    public void setQuantity(Short quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "amount")
    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @ManyToOne
    @JoinColumn(name = "receipt_id", referencedColumnName = "id")
    public ReceiptEntity getReceiptByReceiptId() {
        return receiptByReceiptId;
    }

    public void setReceiptByReceiptId(ReceiptEntity receiptByReceiptId) {
        this.receiptByReceiptId = receiptByReceiptId;
    }

    @ManyToOne
    @JoinColumn(name = "medicine_id", referencedColumnName = "id")
    public MedicineEntity getMedicineByMedicineId() {
        return medicineByMedicineId;
    }

    public void setMedicineByMedicineId(MedicineEntity medicineByMedicineId) {
        this.medicineByMedicineId = medicineByMedicineId;
    }

    @ManyToOne
    @JoinColumn(name = "material_id", referencedColumnName = "id")
    public MaterialEntity getMaterialByMaterialId() {
        return materialByMaterialId;
    }

    public void setMaterialByMaterialId(MaterialEntity materialByMaterialId) {
        this.materialByMaterialId = materialByMaterialId;
    }
}
