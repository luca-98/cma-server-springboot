package com.github.cmateam.cmaserver.dto;

import java.util.UUID;

public class SubclinicalAppointDTO {
    private UUID serviceReportId;
    private UUID serviceId;
    private UUID staffId;
    private Integer status;

    public UUID getServiceReportId() {
        return this.serviceReportId;
    }

    public void setServiceReportId(UUID serviceReportId) {
        this.serviceReportId = serviceReportId;
    }

    public UUID getServiceId() {
        return this.serviceId;
    }

    public void setServiceId(UUID serviceId) {
        this.serviceId = serviceId;
    }

    public UUID getStaffId() {
        return this.staffId;
    }

    public void setStaffId(UUID staffId) {
        this.staffId = staffId;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
