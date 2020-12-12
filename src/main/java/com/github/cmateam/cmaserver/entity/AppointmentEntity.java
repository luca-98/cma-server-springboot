package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "appointment")
public class AppointmentEntity extends BaseEntity {
    private Date dayOfExamination;
    private String time;
    private OrdinalNumberEntity ordinalNumberByOrdinalNumberId;
    private PatientEntity patientByPatientId;
    private StaffEntity staffByStaffId;

    @Basic
    @Column(name = "day_of_examination")
    public Date getDayOfExamination() {
        return dayOfExamination;
    }

    public void setDayOfExamination(Date dayOfExamination) {
        this.dayOfExamination = dayOfExamination;
    }

    @Basic
    @Column(name = "time")
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

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
}
