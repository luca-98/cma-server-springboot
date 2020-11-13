package com.github.cmateam.cmaserver.dto;

import java.util.Date;
import java.util.UUID;

public class HistoryDTO {
    private Integer type;
    private UUID id;
    private String code;
    private String service;
    private Integer quanity;
    private String summary;
    private String mainDisease;
    private String extraDisease;
    private StaffDTO staff;
    private Date createdAt;

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getService() {
        return this.service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Integer getQuanity() {
        return this.quanity;
    }

    public void setQuanity(Integer quanity) {
        this.quanity = quanity;
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

    public String getExtraDisease() {
        return this.extraDisease;
    }

    public void setExtraDisease(String extraDisease) {
        this.extraDisease = extraDisease;
    }

    public StaffDTO getStaff() {
        return this.staff;
    }

    public void setStaff(StaffDTO staff) {
        this.staff = staff;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}
