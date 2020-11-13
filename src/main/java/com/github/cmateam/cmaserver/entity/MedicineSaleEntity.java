package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "medicine_sale", schema = "cma", catalog = "postgres")
public class MedicineSaleEntity extends BaseEntity {
    private List<InvoiceDetailedEntity> invoiceDetailedsById;
    private PrescriptionEntity prescriptionByPrescriptionId;
    private List<MedicineSaleDetailEntity> medicineSaleDetailsById;

    @OneToMany(mappedBy = "medicineSaleByMedicineSaleId")
    public List<InvoiceDetailedEntity> getInvoiceDetailedsById() {
        return invoiceDetailedsById;
    }

    public void setInvoiceDetailedsById(List<InvoiceDetailedEntity> invoiceDetailedsById) {
        this.invoiceDetailedsById = invoiceDetailedsById;
    }

    @ManyToOne
    @JoinColumn(name = "prescription_id", referencedColumnName = "id")
    public PrescriptionEntity getPrescriptionByPrescriptionId() {
        return prescriptionByPrescriptionId;
    }

    public void setPrescriptionByPrescriptionId(PrescriptionEntity prescriptionByPrescriptionId) {
        this.prescriptionByPrescriptionId = prescriptionByPrescriptionId;
    }

    @OneToMany(mappedBy = "medicineSaleByMedicineSaleId")
    public List<MedicineSaleDetailEntity> getMedicineSaleDetailsById() {
        return medicineSaleDetailsById;
    }

    public void setMedicineSaleDetailsById(List<MedicineSaleDetailEntity> medicineSaleDetailsById) {
        this.medicineSaleDetailsById = medicineSaleDetailsById;
    }
}
