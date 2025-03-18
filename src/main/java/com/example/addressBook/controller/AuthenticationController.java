package com.example.addressBook.controller;

import com.example.addressBook.dto.AuthUserDTO;
import com.example.addressBook.service.AuthenticationService;
import com.example.addressBook.service.EmailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.addressBook.dto.LoginDTO;

@RestController
public class AuthenticationController {
    EmailService emailService;
    AuthenticationService authenticationService;

    public AuthenticationController(EmailService emailService, AuthenticationService authenticationService) {
        this.emailService = emailService;
        this.authenticationService = authenticationService;
    }

    //============================UC9(Register and Login for a User)
    @PostMapping(path = "/register")
    public String register(@RequestBody AuthUserDTO user){
        return authenticationService.register(user);
    }

    @PostMapping(path ="/login")
    public String login(@RequestBody LoginDTO user){
        return authenticationService.login(user);
    }
}