package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "receipt")
public class ReceiptEntity extends BaseEntity {
    private Long totalAmount;
    private Long amountPaid;
    private SupplierEntity supplierBySupplierId;
    private StaffEntity staffByStaffId;
    private List<ReceiptDetailEntity> receiptDetailsById;

    @Basic
    @Column(name = "total_amount")
    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Basic
    @Column(name = "amount_paid")
    public Long getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Long amountPaid) {
        this.amountPaid = amountPaid;
    }

    @ManyToOne
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    public SupplierEntity getSupplierBySupplierId() {
        return supplierBySupplierId;
    }

    public void setSupplierBySupplierId(SupplierEntity supplierBySupplierId) {
        this.supplierBySupplierId = supplierBySupplierId;
    }

    @ManyToOne
    @JoinColumn(name = "staff_id", referencedColumnName = "id")
    public StaffEntity getStaffByStaffId() {
        return staffByStaffId;
    }

    public void setStaffByStaffId(StaffEntity staffByStaffId) {
        this.staffByStaffId = staffByStaffId;
    }

    @OneToMany(mappedBy = "receiptByReceiptId")
    public List<ReceiptDetailEntity> getReceiptDetailsById() {
        return receiptDetailsById;
    }

    public void setReceiptDetailsById(List<ReceiptDetailEntity> receiptDetailsById) {
        this.receiptDetailsById = receiptDetailsById;
    }
}
