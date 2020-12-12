package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "medicine")
public class MedicineEntity extends BaseEntity {
    private String medicineName;
    private String medicineNameSearch;
    private Long price;
    private String unitName;
    private Short quantity;
    private GroupMedicineEntity groupMedicineByGroupMedicineId;
    private List<MedicineSaleDetailEntity> medicineSaleDetailsById;
    private List<PrescriptionDetailEntity> prescriptionDetailsById;
    private List<ReceiptDetailEntity> receiptDetailsById;

    @Basic
    @Column(name = "medicine_name")
    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    @Basic
    @Column(name = "medicine_name_search")
    public String getMedicineNameSearch() {
        return this.medicineNameSearch;
    }

    public void setMedicineNameSearch(String medicineNameSearch) {
        this.medicineNameSearch = medicineNameSearch;
    }

    @Basic
    @Column(name = "price")
    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Basic
    @Column(name = "unit_name")
    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @Basic
    @Column(name = "quantity")
    public Short getQuantity() {
        return quantity;
    }

    public void setQuantity(Short quantity) {
        this.quantity = quantity;
    }

    @ManyToOne
    @JoinColumn(name = "group_medicine_id", referencedColumnName = "id")
    public GroupMedicineEntity getGroupMedicineByGroupMedicineId() {
        return groupMedicineByGroupMedicineId;
    }

    public void setGroupMedicineByGroupMedicineId(GroupMedicineEntity groupMedicineByGroupMedicineId) {
        this.groupMedicineByGroupMedicineId = groupMedicineByGroupMedicineId;
    }

    @OneToMany(mappedBy = "medicineByMedicineId")
    public List<MedicineSaleDetailEntity> getMedicineSaleDetailsById() {
        return medicineSaleDetailsById;
    }

    public void setMedicineSaleDetailsById(List<MedicineSaleDetailEntity> medicineSaleDetailsById) {
        this.medicineSaleDetailsById = medicineSaleDetailsById;
    }

    @OneToMany(mappedBy = "medicineByMedicineId")
    public List<PrescriptionDetailEntity> getPrescriptionDetailsById() {
        return prescriptionDetailsById;
    }

    public void setPrescriptionDetailsById(List<PrescriptionDetailEntity> prescriptionDetailsById) {
        this.prescriptionDetailsById = prescriptionDetailsById;
    }

    @OneToMany(mappedBy = "medicineByMedicineId")
    public List<ReceiptDetailEntity> getReceiptDetailsById() {
        return receiptDetailsById;
    }

    public void setReceiptDetailsById(List<ReceiptDetailEntity> receiptDetailsById) {
        this.receiptDetailsById = receiptDetailsById;
    }
}
