package com.github.cmateam.cmaserver.controller;

import java.security.Principal;

import com.github.cmateam.cmaserver.dto.UserLoginDTO;
import com.github.cmateam.cmaserver.service.AuthenticationServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private AuthenticationServiceImpl authenticationServiceImpl;

    @Autowired
    public AuthenticationController(AuthenticationServiceImpl authenticationServiceImpl) {
        this.authenticationServiceImpl = authenticationServiceImpl;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam("username") String userName,
            @RequestParam("password") String password) {
        UserLoginDTO result = authenticationServiceImpl.login(userName, password);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestParam("oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword, Principal principal) {
        return authenticationServiceImpl.changePassword(oldPassword, newPassword, principal);
    }
}
