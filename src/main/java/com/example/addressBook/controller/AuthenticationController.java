package com.example.addressBook.controller;

import com.example.addressBook.dto.AuthUserDTO;
import com.example.addressBook.dto.LoginDTO;
import com.example.addressBook.dto.PasswordResetDTO;
import com.example.addressBook.service.AuthenticationService;
import com.example.addressBook.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.addressBook.dto.MailDTO;

@RestController
@RequestMapping("/auth")  // 🔹 Base URL for all authentication endpoints
public class AuthenticationController {

    private final EmailService emailService;  // Declare EmailService as a final field
    private final AuthenticationService authenticationService;

    // Constructor Injection for both AuthenticationService and EmailService
    public AuthenticationController(AuthenticationService authenticationService, EmailService emailService) {
        this.authenticationService = authenticationService;
        this.emailService = emailService;  // Initialize emailService via constructor
    }

    // 🔹 Register User
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthUserDTO user) {
        return ResponseEntity.ok(authenticationService.register(user));
    }

    // 🔹 Login User
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO user) {
        return ResponseEntity.ok(authenticationService.login(user));
    }

    // 🔹 Forgot Password (User provides email & new phone)
    @PutMapping("/forgotPassword/{email}")
    public ResponseEntity<String> forgotPassword(@PathVariable String email, @RequestBody PasswordResetDTO passwordResetDTO) {
        return ResponseEntity.ok(authenticationService.forgotPassword(email, passwordResetDTO.getNewPhone()));
    }

    // 🔹 Reset Password (User provides email, current phone & new phone)
    @PutMapping("/resetPassword/{email}")
    public ResponseEntity<String> resetPassword(@PathVariable String email, @RequestBody PasswordResetDTO passwordResetDTO) {
        return ResponseEntity.ok(authenticationService.resetPassword(email, passwordResetDTO.getCurrentPhone(), passwordResetDTO.getNewPhone()));
    }

    //==============================Sendmail======================//
    @PostMapping(path="/sendMail")
    public String sendMail(@RequestBody MailDTO user){ emailService.sendEmail(user.getTo(), user.getSubject(), user.getBody());
        return "Mail Sent Successfully";
    }

}