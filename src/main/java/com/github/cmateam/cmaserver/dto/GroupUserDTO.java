package com.github.cmateam.cmaserver.dto;

import java.util.Date;
import java.util.UUID;

public class GroupUserDTO {
    private UUID id;
    private String userGroupCode;
    private String userGroupName;
    private Integer status;
    private Date createdAt;
    private Date updatedAt;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserGroupCode() {
        return this.userGroupCode;
    }

    public void setUserGroupCode(String userGroupCode) {
        this.userGroupCode = userGroupCode;
    }

    public String getUserGroupName() {
        return this.userGroupName;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
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
