package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "service_report")
public class ServiceReportEntity extends BaseEntity {
    private String result;
    private String htmlReport;
    private String note;
    private List<ImageEntity> imagesById;
    private ServiceEntity serviceByServiceId;
    private StaffEntity staffByStaffId;
    private MedicalExaminationEntity medicalExaminationByMedicalExaminationId;

    @Basic
    @Column(name = "result")
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Basic
    @Column(name = "html_report")
    public String getHtmlReport() {
        return htmlReport;
    }

    public void setHtmlReport(String htmlReport) {
        this.htmlReport = htmlReport;
    }

    @Basic
    @Column(name = "note")
    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @OneToMany(mappedBy = "serviceReportByServiceReportId")
    public List<ImageEntity> getImagesById() {
        return imagesById;
    }

    public void setImagesById(List<ImageEntity> imagesById) {
        this.imagesById = imagesById;
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
}
