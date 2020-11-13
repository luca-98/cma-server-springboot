package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;

@Entity
@Table(name = "receive_patient", schema = "cma", catalog = "postgres")
public class ReceivePatientEntity extends BaseEntity {
    private OrdinalNumberEntity ordinalNumberByOrdinalNumberId;
    private PatientEntity patientByPatientId;
    private StaffEntity staffByStaffId;
    private MedicalExaminationEntity medicalExaminationByMedicalExaminationId;

    @ManyToOne
    @JoinColumn(name = "ordinal_number_id", referencedColumnName = "id")
    public OrdinalNumberEntity getOrdinalNumberByOrdinalNumberId() {
        return ordinalNumberByOrdinalNumberId;
    }

    public void setOrdinalNumberByOrdinalNumberId(OrdinalNumberEntity ordinalNumberByOrdinalNumberId) {
        this.ordinalNumberByOrdinalNumberId = ordinalNumberByOrdinalNumberId;
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

    @ManyToOne
    @JoinColumn(name = "medical_examination_id", referencedColumnName = "id")
    public MedicalExaminationEntity getMedicalExaminationByMedicalExaminationId() {
        return this.medicalExaminationByMedicalExaminationId;
    }

    public void setMedicalExaminationByMedicalExaminationId(MedicalExaminationEntity medicalExaminationByMedicalExaminationId) {
        this.medicalExaminationByMedicalExaminationId = medicalExaminationByMedicalExaminationId;
    }

}
