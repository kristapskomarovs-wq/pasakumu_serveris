package com.komarovs.pasakumu_serveris.controllers;

import com.komarovs.pasakumu_serveris.models.UserModel;
import com.komarovs.pasakumu_serveris.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // POST /api/v1/register — Reģistrēt jaunu lietotāju
    @PostMapping("/api/v1/register")
    public ResponseEntity<?> register(@RequestBody UserModel user) {
        try {
            Long id = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /api/v1/checkemail/{email} — Pārbaudīt vai e-pasts jau eksistē
    @GetMapping("/api/v1/checkemail/{email}")
    public ResponseEntity<Boolean> checkEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.checkEmail(email));
    }

    // POST /api/v1/login
    @PostMapping("/api/v1/login")
    public ResponseEntity<?> login(@RequestBody UserModel user) {
        Long userId = userService.signIn(user);
        if (userId != null) {
            return ResponseEntity.ok(userId);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nepareizs e-pasts vai parole!");
    }
}