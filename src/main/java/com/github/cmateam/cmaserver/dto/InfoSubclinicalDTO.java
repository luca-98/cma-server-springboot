package com.github.cmateam.cmaserver.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class InfoSubclinicalDTO {
    private UUID medicalExamId;
    private String medicalExaminationCode;
    private UUID patientId;
    private String patientCode;
    private String patientName;
    private String phone;
    private Date dateOfBirth;
    private String dateOfBirthStr;
    private Integer gender;
    private String address;
    private List<SubclinicalAppointDTO> listAppoint;

    public UUID getMedicalExamId() {
        return this.medicalExamId;
    }

    public void setMedicalExamId(UUID medicalExamId) {
        this.medicalExamId = medicalExamId;
    }

    public String getMedicalExaminationCode() {
        return this.medicalExaminationCode;
    }

    public void setMedicalExaminationCode(String medicalExaminationCode) {
        this.medicalExaminationCode = medicalExaminationCode;
    }

    public UUID getPatientId() {
        return this.patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
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

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDateOfBirthStr() {
        return this.dateOfBirthStr;
    }

    public void setDateOfBirthStr(String dateOfBirthStr) {
        this.dateOfBirthStr = dateOfBirthStr;
    }

    public Integer getGender() {
        return this.gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<SubclinicalAppointDTO> getListAppoint() {
        return this.listAppoint;
    }

    public void setListAppoint(List<SubclinicalAppointDTO> listAppoint) {
        this.listAppoint = listAppoint;
    }

}
