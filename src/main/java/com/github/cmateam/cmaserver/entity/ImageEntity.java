package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;

@Entity
@Table(name = "image")
public class ImageEntity extends BaseEntity {
    private ServiceReportEntity serviceReportByServiceReportId;

    @ManyToOne
    @JoinColumn(name = "service_report_id", referencedColumnName = "id")
    public ServiceReportEntity getServiceReportByServiceReportId() {
        return serviceReportByServiceReportId;
    }

    public void setServiceReportByServiceReportId(ServiceReportEntity serviceReportByServiceReportId) {
        this.serviceReportByServiceReportId = serviceReportByServiceReportId;
    }
}
