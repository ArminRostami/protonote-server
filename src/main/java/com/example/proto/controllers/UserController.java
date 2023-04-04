package com.example.proto.controllers;

import com.example.proto.models.User;
import com.example.proto.services.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody User user) {
        this.userService.signUp(user.getUsername(), user.getPassword());
    }

}