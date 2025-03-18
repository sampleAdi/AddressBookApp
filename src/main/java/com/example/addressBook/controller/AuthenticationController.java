package com.example.addressBook.controller;

import com.example.addressBook.dto.AuthUserDTO;
import com.example.addressBook.dto.LoginDTO;
import com.example.addressBook.dto.PasswordResetDTO;
import com.example.addressBook.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")  // 🔹 Base URL for all authentication endpoints
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
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
}