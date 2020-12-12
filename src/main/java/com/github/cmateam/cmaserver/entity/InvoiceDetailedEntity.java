package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;

@Entity
@Table(name = "invoice_detailed")
public class InvoiceDetailedEntity extends BaseEntity {
    private Short quantity;
    private Long amount;
    private Long amountPaid;
    private InvoiceEntity invoiceByInvoiceId;
    private ServiceEntity serviceByServiceId;
    private MedicineSaleEntity medicineSaleByMedicineSaleId;

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

    @Basic
    @Column(name = "amount_paid")
    public Long getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Long amountPaid) {
        this.amountPaid = amountPaid;
    }

    @ManyToOne
    @JoinColumn(name = "invoice_id", referencedColumnName = "id")
    public InvoiceEntity getInvoiceByInvoiceId() {
        return invoiceByInvoiceId;
    }

    public void setInvoiceByInvoiceId(InvoiceEntity invoiceByInvoiceId) {
        this.invoiceByInvoiceId = invoiceByInvoiceId;
    }

    @ManyToOne
    @JoinColumn(name = "service_id", referencedColumnName = "id")
    public ServiceEntity getServiceByServiceId() {
        return serviceByServiceId;
    }

    public void setServiceByServiceId(ServiceEntity serviceByServiceId) {
        this.serviceByServiceId = serviceByServiceId;
    }

    @ManyToOne
    @JoinColumn(name = "medicine_sale_id", referencedColumnName = "id")
    public MedicineSaleEntity getMedicineSaleByMedicineSaleId() {
        return medicineSaleByMedicineSaleId;
    }

    public void setMedicineSaleByMedicineSaleId(MedicineSaleEntity medicineSaleByMedicineSaleId) {
        this.medicineSaleByMedicineSaleId = medicineSaleByMedicineSaleId;
    }
}
