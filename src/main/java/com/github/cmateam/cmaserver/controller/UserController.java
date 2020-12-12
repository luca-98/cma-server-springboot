package com.github.cmateam.cmaserver.controller;

import java.util.List;
import java.util.UUID;

import com.github.cmateam.cmaserver.dto.UserDTO;
import com.github.cmateam.cmaserver.service.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserServiceImpl userServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping
    public List<UserDTO> getAll() {
        return userServiceImpl.getAll();
    }

    @PostMapping
    public UserDTO addNewUser(@RequestBody UserDTO newUser) {
        return userServiceImpl.addNewUser(newUser);
    }

    @PutMapping
    public UserDTO updateUser(@RequestBody UserDTO newUser) {
        return userServiceImpl.updateUser(newUser);
    }

    @PutMapping(value = "change-password/{id}")
    public Boolean changePassword(@PathVariable UUID id, @RequestParam String password) {
        return userServiceImpl.changePassword(id, password);
    }

    @DeleteMapping(value = "{id}")
    public void deleteUser(@PathVariable UUID id) {
        userServiceImpl.delete(id);
    }

    @PutMapping(value = "change-status-staff/{id}")
    public Boolean changeStatusStaff(@PathVariable UUID id, @RequestParam Integer status) {
        return userServiceImpl.changeStatusStaff(id, status);
    }
}
