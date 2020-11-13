package com.github.cmateam.cmaserver.dto;

import java.util.Date;
import java.util.UUID;

public class MedicalExamTableDTO {
    private UUID id;
    private Date dayOfExam;
    private String medicalExaminationCode;
    private String patientCode;
    private String patientName;
    private String address;
    private String phone;
    private Integer status;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getDayOfExam() {
        return this.dayOfExam;
    }

    public void setDayOfExam(Date dayOfExam) {
        this.dayOfExam = dayOfExam;
    }

    public String getMedicalExaminationCode() {
        return this.medicalExaminationCode;
    }

    public void setMedicalExaminationCode(String medicalExaminationCode) {
        this.medicalExaminationCode = medicalExaminationCode;
    }

    public String getPatientCode() {
        return this.patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    public String getPatientName() {
        return this.patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
