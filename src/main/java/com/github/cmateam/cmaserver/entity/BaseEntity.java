package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import java.util.Date;
import java.util.UUID;

@MappedSuperclass
public class BaseEntity {
    private UUID id;
    private Integer status;
    private Date createdAt;
    private Date updatedAt;

    @Id
    @Column(name = "id")
    @GeneratedValue
    @Type(type = "pg-uuid")
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "created_at")
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "updated_at")
    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
