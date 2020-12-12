package com.github.cmateam.cmaserver.dto;

import java.util.Date;
import java.util.UUID;

public class MedicalExamDTO {
    private UUID id;
    private String medicalExaminationCode;
    private String examinationReason;
    private String bloodVessel;
    private String bloodPressure;
    private String breathing;
    private String temperature;
    private String height;
    private String weight;
    private String symptom;
    private String summary;
    private String mainDisease;
    private String mainDiseaseCode;
    private String extraDisease;
    private String extraDiseaseCode;
    private StaffDTO staff;
    private PatientDTO patient;
    private RoomServiceDTO roomService;
    private Long clinicalPrice;
    private Integer payingStatus;
    private Integer status;
    private Date createdAt;
    private Date updatedAt;
    private String printDataHtml;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMedicalExaminationCode() {
        return this.medicalExaminationCode;
    }

    public void setMedicalExaminationCode(String medicalExaminationCode) {
        this.medicalExaminationCode = medicalExaminationCode;
    }

    public String getExaminationReason() {
        return this.examinationReason;
    }

    public void setExaminationReason(String examinationReason) {
        this.examinationReason = examinationReason;
    }

    public String getBloodVessel() {
        return this.bloodVessel;
    }

    public void setBloodVessel(String bloodVessel) {
        this.bloodVessel = bloodVessel;
    }

    public String getBloodPressure() {
        return this.bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getBreathing() {
        return this.breathing;
    }

    public void setBreathing(String breathing) {
        this.breathing = breathing;
    }

    public String getTemperature() {
        return this.temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHeight() {
        return this.height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return this.weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getSymptom() {
        return this.symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getMainDisease() {
        return this.mainDisease;
    }

    public void setMainDisease(String mainDisease) {
        this.mainDisease = mainDisease;
    }

    public String getMainDiseaseCode() {
        return this.mainDiseaseCode;
    }

    public void setMainDiseaseCode(String mainDiseaseCode) {
        this.mainDiseaseCode = mainDiseaseCode;
    }

    public String getExtraDisease() {
        return this.extraDisease;
    }

    public void setExtraDisease(String extraDisease) {
        this.extraDisease = extraDisease;
    }

    public String getExtraDiseaseCode() {
        return this.extraDiseaseCode;
    }

    public void setExtraDiseaseCode(String extraDiseaseCode) {
        this.extraDiseaseCode = extraDiseaseCode;
    }

    public StaffDTO getStaff() {
        return this.staff;
    }

    public void setStaff(StaffDTO staff) {
        this.staff = staff;
    }

    public PatientDTO getPatient() {
        return this.patient;
    }

    public void setPatient(PatientDTO patient) {
        this.patient = patient;
    }

    public RoomServiceDTO getRoomService() {
        return this.roomService;
    }

    public void setRoomService(RoomServiceDTO roomService) {
        this.roomService = roomService;
    }

    public Long getClinicalPrice() {
        return this.clinicalPrice;
    }

    public void setClinicalPrice(Long clinicalPrice) {
        this.clinicalPrice = clinicalPrice;
    }

    public Integer getPayingStatus() {
        return this.payingStatus;
    }

    public void setPayingStatus(Integer payingStatus) {
        this.payingStatus = payingStatus;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPrintDataHtml() {
        return this.printDataHtml;
    }

    public void setPrintDataHtml(String printDataHtml) {
        this.printDataHtml = printDataHtml;
    }

}
