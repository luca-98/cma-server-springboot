package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;

@Entity
@Table(name = "manager", schema = "cma", catalog = "postgres")
public class ManagerEntity extends BaseEntity {
    private String fullName;
    private String email;
    private AppUserEntity appUserByAppUserId;

    @Basic
    @Column(name = "full_name")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ManyToOne
    @JoinColumn(name = "app_user_id", referencedColumnName = "id", nullable = false)
    public AppUserEntity getAppUserByAppUserId() {
        return appUserByAppUserId;
    }

    public void setAppUserByAppUserId(AppUserEntity appUserByAppUserId) {
        this.appUserByAppUserId = appUserByAppUserId;
    }
}
