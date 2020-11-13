package com.github.cmateam.cmaserver.dto;

import java.util.List;
import java.util.UUID;

public class UserLoginDTO {
    private String token;
    private String userName;
    private String fullName;
    private String roomName;
    private String userGroupCode;
    private List<String> permissionCode;
    private UUID staffId;
    private UUID roomId;

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRoomName() {
        return this.roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getUserGroupCode() {
        return this.userGroupCode;
    }

    public void setUserGroupCode(String userGroupCode) {
        this.userGroupCode = userGroupCode;
    }

    public List<String> getPermissionCode() {
        return this.permissionCode;
    }

    public void setPermissionCode(List<String> permissionCode) {
        this.permissionCode = permissionCode;
    }

    public UUID getStaffId() {
        return this.staffId;
    }

    public void setStaffId(UUID staffId) {
        this.staffId = staffId;
    }

    public UUID getRoomId() {
        return this.roomId;
    }

    public void setRoomId(UUID roomId) {
        this.roomId = roomId;
    }

}
