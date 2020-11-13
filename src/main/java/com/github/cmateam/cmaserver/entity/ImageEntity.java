package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;

@Entity
@Table(name = "image", schema = "cma", catalog = "postgres")
public class ImageEntity extends BaseEntity {
    private String imagePath;
    private ServiceReportEntity serviceReportByServiceReportId;

    @Basic
    @Column(name = "image_path")
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @ManyToOne
    @JoinColumn(name = "service_report_id", referencedColumnName = "id")
    public ServiceReportEntity getServiceReportByServiceReportId() {
        return serviceReportByServiceReportId;
    }

    public void setServiceReportByServiceReportId(ServiceReportEntity serviceReportByServiceReportId) {
        this.serviceReportByServiceReportId = serviceReportByServiceReportId;
    }
}
