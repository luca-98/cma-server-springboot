package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "prescription", schema = "cma", catalog = "postgres")
public class PrescriptionEntity extends BaseEntity {
    private String note;
    private List<MedicineSaleEntity> medicineSalesById;
    private StaffEntity staffByStaffId;
    private MedicalExaminationEntity medicalExaminationByMedicalExaminationId;
    private List<PrescriptionDetailEntity> prescriptionDetailsById;

    @Basic
    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @OneToMany(mappedBy = "prescriptionByPrescriptionId")
    public List<MedicineSaleEntity> getMedicineSalesById() {
        return medicineSalesById;
    }

    public void setMedicineSalesById(List<MedicineSaleEntity> medicineSalesById) {
        this.medicineSalesById = medicineSalesById;
    }

    @ManyToOne
    @JoinColumn(name = "staff_id", referencedColumnName = "id")
    public StaffEntity getStaffByStaffId() {
        return staffByStaffId;
    }

    public void setStaffByStaffId(StaffEntity staffByStaffId) {
        this.staffByStaffId = staffByStaffId;
    }

    @ManyToOne
    @JoinColumn(name = "medical_examination_id", referencedColumnName = "id")
    public MedicalExaminationEntity getMedicalExaminationByMedicalExaminationId() {
        return medicalExaminationByMedicalExaminationId;
    }

    public void setMedicalExaminationByMedicalExaminationId(
            MedicalExaminationEntity medicalExaminationByMedicalExaminationId) {
        this.medicalExaminationByMedicalExaminationId = medicalExaminationByMedicalExaminationId;
    }

    @OneToMany(mappedBy = "prescriptionByPrescriptionId")
    public List<PrescriptionDetailEntity> getPrescriptionDetailsById() {
        return prescriptionDetailsById;
    }

    public void setPrescriptionDetailsById(List<PrescriptionDetailEntity> prescriptionDetailsById) {
        this.prescriptionDetailsById = prescriptionDetailsById;
    }
}
