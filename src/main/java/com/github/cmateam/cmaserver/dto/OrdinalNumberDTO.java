package com.github.cmateam.cmaserver.dto;

import java.util.Date;
import java.util.UUID;

public class OrdinalNumberDTO {
    private UUID id;
    private Date dayOfExamination;
    private Short ordinalNumber;
    private Integer status;
    private Date createdAt;
    private Date updatedAt;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getDayOfExamination() {
        return this.dayOfExamination;
    }

    public void setDayOfExamination(Date dayOfExamination) {
        this.dayOfExamination = dayOfExamination;
    }

    public Short getOrdinalNumber() {
        return this.ordinalNumber;
    }

    public void setOrdinalNumber(Short ordinalNumber) {
        this.ordinalNumber = ordinalNumber;
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

}
