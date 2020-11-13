package com.github.cmateam.cmaserver.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "app_user", schema = "cma", catalog = "postgres")
public class AppUserEntity extends BaseEntity {
    private String userName;
    private String encryptedPassword;
    private UserGroupEntity userGroupByUserGroupId;
    private List<ManagerEntity> managersById;
    private List<StaffEntity> staffById;

    @Basic
    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "encrypted_password")
    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    @ManyToOne
    @JoinColumn(name = "user_group_id", referencedColumnName = "id", nullable = false)
    public UserGroupEntity getUserGroupByUserGroupId() {
        return userGroupByUserGroupId;
    }

    public void setUserGroupByUserGroupId(UserGroupEntity userGroupByUserGroupId) {
        this.userGroupByUserGroupId = userGroupByUserGroupId;
    }

    @OneToMany(mappedBy = "appUserByAppUserId")
    public List<ManagerEntity> getManagersById() {
        return managersById;
    }

    public void setManagersById(List<ManagerEntity> managersById) {
        this.managersById = managersById;
    }

    @OneToMany(mappedBy = "appUserByAppUserId")
    public List<StaffEntity> getStaffById() {
        return staffById;
    }

    public void setStaffById(List<StaffEntity> staffById) {
        this.staffById = staffById;
    }
}
