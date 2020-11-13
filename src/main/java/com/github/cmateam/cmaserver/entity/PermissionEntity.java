package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Table(name = "permission", schema = "cma", catalog = "postgres")
public class PermissionEntity extends BaseEntity {
    private String permissionCode;
    private String permissionName;
    private List<UserGroupEntity> userGroupById;

    @Basic
    @Column(name = "permission_code")
    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    @Basic
    @Column(name = "permission_name")
    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_group_permission", joinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "user_group_id", referencedColumnName = "id", nullable = false))
    public List<UserGroupEntity> getUserGroupById() {
        return userGroupById;
    }

    public void setUserGroupById(List<UserGroupEntity> userGroupById) {
        this.userGroupById = userGroupById;
    }
}
