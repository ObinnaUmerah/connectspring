package com.example.connect.controller;

import com.example.connect.model.User;
import com.example.connect.model.request.LoginRequest;
import com.example.connect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth/")
public class UserController {

    private UserService userService;

    @Autowired
    public void SetUserService(UserService userService) {
        this.userService = userService;
    }

    // http://localhost:8080/auth/users/register/
    @PostMapping(path = "/register/")
    public User createUser(@RequestBody User userObject) {
        return userService.createUser(userObject);
    }

    // http://localhost:8080/auth/users/login/
    @PostMapping("/login/")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        return userService.loginUser(loginRequest);
    }



}