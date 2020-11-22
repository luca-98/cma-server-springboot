package com.github.cmateam.cmaserver.dto;

import java.util.UUID;

public class SubclinicalAppointDTO {
    private UUID serviceReportId;
    private UUID serviceId;
    private UUID staffId;
    private String summary;
    private String note;
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

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
