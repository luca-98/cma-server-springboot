package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "invoice", schema = "cma", catalog = "postgres")
public class InvoiceEntity extends BaseEntity {
    private Long totalAmount;
    private Long amountPaid;
    private PatientEntity patientByPatientId;
    private StaffEntity staffByStaffId;
    private List<InvoiceDetailedEntity> invoiceDetailedsById;
    private MedicalExaminationEntity medicalExaminationByMedicalExaminationId;

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
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    public PatientEntity getPatientByPatientId() {
        return patientByPatientId;
    }

    public void setPatientByPatientId(PatientEntity patientByPatientId) {
        this.patientByPatientId = patientByPatientId;
    }

    @ManyToOne
    @JoinColumn(name = "staff_id", referencedColumnName = "id")
    public StaffEntity getStaffByStaffId() {
        return staffByStaffId;
    }

    public void setStaffByStaffId(StaffEntity staffByStaffId) {
        this.staffByStaffId = staffByStaffId;
    }

    @OneToMany(mappedBy = "invoiceByInvoiceId", orphanRemoval = true)
    public List<InvoiceDetailedEntity> getInvoiceDetailedsById() {
        return invoiceDetailedsById;
    }

    public void setInvoiceDetailedsById(List<InvoiceDetailedEntity> invoiceDetailedsById) {
        this.invoiceDetailedsById = invoiceDetailedsById;
    }

    @ManyToOne
    @JoinColumn(name = "medical_examination_id", referencedColumnName = "id")
    public MedicalExaminationEntity getMedicalExaminationByMedicalExaminationId() {
        return medicalExaminationByMedicalExaminationId;
    }

    public void setMedicalExaminationByMedicalExaminationId(MedicalExaminationEntity medicalExaminationByMedicalExaminationId) {
        this.medicalExaminationByMedicalExaminationId = medicalExaminationByMedicalExaminationId;
    }
}
