package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
@Table(name = "user_group", schema = "cma", catalog = "postgres")
public class UserGroupEntity extends BaseEntity {
    private String userGroupCode;
    private String userGroupName;
    private List<AppUserEntity> appUsersById;
    private List<PermissionEntity> permissionsById;

    @Basic
    @Column(name = "user_group_code")
    public String getUserGroupCode() {
        return userGroupCode;
    }

    public void setUserGroupCode(String userGroupCode) {
        this.userGroupCode = userGroupCode;
    }

    @Basic
    @Column(name = "user_group_name")
    public String getUserGroupName() {
        return userGroupName;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }

    @OneToMany(mappedBy = "userGroupByUserGroupId")
    public List<AppUserEntity> getAppUsersById() {
        return appUsersById;
    }

    public void setAppUsersById(List<AppUserEntity> appUsersById) {
        this.appUsersById = appUsersById;
    }

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "user_group_permission", joinColumns = @JoinColumn(name = "user_group_id", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id", nullable = false))
    public List<PermissionEntity> getPermissionsById() {
        return permissionsById;
    }

    public void setPermissionsById(List<PermissionEntity> permissionsById) {
        this.permissionsById = permissionsById;
    }
}
