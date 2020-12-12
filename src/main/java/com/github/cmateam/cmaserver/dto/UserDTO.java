package com.github.cmateam.cmaserver.dto;

import java.util.Date;
import java.util.UUID;

public class UserDTO {
    private UUID staffId;
    private UUID appUserId;
    private UUID userGroupId;
    private String userName;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private Date dateOfBirth;
    private String userGroupName;
    private String userGroupCode;
    private Integer status;

    public UUID getStaffId() {
        return this.staffId;
    }

    public void setStaffId(UUID staffId) {
        this.staffId = staffId;
    }

    public UUID getAppUserId() {
        return this.appUserId;
    }

    public void setAppUserId(UUID appUserId) {
        this.appUserId = appUserId;
    }

    public UUID getUserGroupId() {
        return this.userGroupId;
    }

    public void setUserGroupId(UUID userGroupId) {
        this.userGroupId = userGroupId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getUserGroupName() {
        return this.userGroupName;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }

    public String getUserGroupCode() {
        return this.userGroupCode;
    }

    public void setUserGroupCode(String userGroupCode) {
        this.userGroupCode = userGroupCode;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
