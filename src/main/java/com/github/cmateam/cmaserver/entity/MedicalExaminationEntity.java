package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "medical_examination", schema = "cma", catalog = "postgres")
public class MedicalExaminationEntity extends BaseEntity {
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
    private StaffEntity staffByStaffId;
    private PatientEntity patientByPatientId;
    private List<PrescriptionEntity> prescriptionsById;
    private List<ServiceReportEntity> serviceReportsById;
    private List<ReceivePatientEntity> receivePatientsById;
    private List<InvoiceEntity> invoicesById;

    @Basic
    @Column(name = "medical_examination_code")
    public String getMedicalExaminationCode() {
        return medicalExaminationCode;
    }

    public void setMedicalExaminationCode(String medicalExaminationCode) {
        this.medicalExaminationCode = medicalExaminationCode;
    }

    @Basic
    @Column(name = "examination_reason")
    public String getExaminationReason() {
        return examinationReason;
    }

    public void setExaminationReason(String examinationReason) {
        this.examinationReason = examinationReason;
    }

    @Basic
    @Column(name = "blood_vessel")
    public String getBloodVessel() {
        return bloodVessel;
    }

    public void setBloodVessel(String bloodVessel) {
        this.bloodVessel = bloodVessel;
    }

    @Basic
    @Column(name = "blood_pressure")
    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    @Basic
    @Column(name = "breathing")
    public String getBreathing() {
        return breathing;
    }

    public void setBreathing(String breathing) {
        this.breathing = breathing;
    }

    @Basic
    @Column(name = "temperature")
    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    @Basic
    @Column(name = "height")
    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    @Basic
    @Column(name = "weight")
    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name = "symptom")
    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    @Basic
    @Column(name = "summary")
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Basic
    @Column(name = "main_disease")
    public String getMainDisease() {
        return this.mainDisease;
    }

    public void setMainDisease(String mainDisease) {
        this.mainDisease = mainDisease;
    }

    @Basic
    @Column(name = "main_disease_code")
    public String getMainDiseaseCode() {
        return this.mainDiseaseCode;
    }

    public void setMainDiseaseCode(String mainDiseaseCode) {
        this.mainDiseaseCode = mainDiseaseCode;
    }

    @Basic
    @Column(name = "extra_disease")
    public String getExtraDisease() {
        return this.extraDisease;
    }

    public void setExtraDisease(String extraDisease) {
        this.extraDisease = extraDisease;
    }

    @Basic
    @Column(name = "extra_disease_code")
    public String getExtraDiseaseCode() {
        return this.extraDiseaseCode;
    }

    public void setExtraDiseaseCode(String extraDiseaseCode) {
        this.extraDiseaseCode = extraDiseaseCode;
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
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    public PatientEntity getPatientByPatientId() {
        return patientByPatientId;
    }

    public void setPatientByPatientId(PatientEntity patientByPatientId) {
        this.patientByPatientId = patientByPatientId;
    }

    @OneToMany(mappedBy = "medicalExaminationByMedicalExaminationId")
    public List<PrescriptionEntity> getPrescriptionsById() {
        return prescriptionsById;
    }

    public void setPrescriptionsById(List<PrescriptionEntity> prescriptionsById) {
        this.prescriptionsById = prescriptionsById;
    }

    @OneToMany(mappedBy = "medicalExaminationByMedicalExaminationId")
    public List<ServiceReportEntity> getServiceReportsById() {
        return serviceReportsById;
    }

    public void setServiceReportsById(List<ServiceReportEntity> serviceReportsById) {
        this.serviceReportsById = serviceReportsById;
    }

    @OneToMany(mappedBy = "medicalExaminationByMedicalExaminationId")
    public List<ReceivePatientEntity> getReceivePatientsById() {
        return this.receivePatientsById;
    }

    public void setReceivePatientsById(List<ReceivePatientEntity> receivePatientsById) {
        this.receivePatientsById = receivePatientsById;
    }

    @OneToMany(mappedBy = "medicalExaminationByMedicalExaminationId")
    public List<InvoiceEntity> getInvoicesById() {
        return this.invoicesById;
    }

    public void setInvoicesById(List<InvoiceEntity> invoicesById) {
        this.invoicesById = invoicesById;
    }
}
