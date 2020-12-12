package com.github.cmateam.cmaserver.controller;

import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.dto.GroupUserDTO;
import com.github.cmateam.cmaserver.dto.PermissionDTO;
import com.github.cmateam.cmaserver.service.GroupUserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/group-user")
public class GroupUserController {

    private GroupUserServiceImpl groupUserServiceImpl;

    @Autowired
    public GroupUserController(GroupUserServiceImpl groupUserServiceImpl) {
        this.groupUserServiceImpl = groupUserServiceImpl;
    }

    @GetMapping
    public List<GroupUserDTO> getAll() {
        return groupUserServiceImpl.getAll();
    }

    @GetMapping(value = "/get-list-user/{id}")
    public List<UUID> getUserIdFromGroupUser(@PathVariable UUID id) {
        return groupUserServiceImpl.getUserIdFromGroupUser(id);
    }

    @DeleteMapping(value = "{id}")
    public void deleteUser(@PathVariable UUID id) {
        groupUserServiceImpl.deleteUser(id);
    }

    @GetMapping("get-all-permission")
    public List<PermissionDTO> getAllPermission() {
        return groupUserServiceImpl.getAllPermission();
    }

    @GetMapping(value = "get-all-permission-by-group-user/{id}")
    public List<PermissionDTO> getAllPermissionByGroupuser(@PathVariable UUID id) {
        return groupUserServiceImpl.getAllPermissionByGroupUser(id);
    }

    @PostMapping("add-group-user")
    public GroupUserDTO addGroupUser(@RequestParam String groupName, @RequestParam List<UUID> listPermission) {
        return groupUserServiceImpl.addNewGroupUser(groupName, listPermission);
    }

    @PutMapping(value = "update-group-user/{id}")
    public GroupUserDTO updateGroupUser(@PathVariable UUID id, @RequestParam String groupName,
            @RequestParam List<UUID> listPermission) {
        return groupUserServiceImpl.updateGroupUser(id, groupName, listPermission);
    }
}
