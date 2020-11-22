package com.github.cmateam.cmaserver.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class GroupTemplateDTO {
    private UUID id;
    private String groupTemplateName;
    private Date createdAt;
    private Date updatedAt;
    private Integer status;
    private List<UUID> listTemplateId;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getGroupTemplateName() {
        return this.groupTemplateName;
    }

    public void setGroupTemplateName(String groupTemplateName) {
        this.groupTemplateName = groupTemplateName;
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

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<UUID> getListTemplateId() {
        return this.listTemplateId;
    }

    public void setListTemplateId(List<UUID> listTemplateId) {
        this.listTemplateId = listTemplateId;
    }

}
