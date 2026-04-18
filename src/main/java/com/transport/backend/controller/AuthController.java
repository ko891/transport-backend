package com.transport.backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.transport.backend.model.Citoyen;
import com.transport.backend.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    // Repository direct
    @Autowired
    private AuthService authService;

    @PostMapping("/send-code")
    public ResponseEntity<?> sendCode(@RequestBody Map<String, String> body) {
      try {
        return ResponseEntity.ok(authService.envoyerCode(body.get("email")));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/verify-code")
    public ResponseEntity<?> verifyCode(@RequestBody Map<String, String> body) {
      try {
        return ResponseEntity.ok(authService.verifierCode(body.get("email"), body.get("code")));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Citoyen citoyen) {
      try {
         return ResponseEntity.ok(authService.inscrire(citoyen));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
      try {
        return ResponseEntity.ok(authService.connecter(body.get("email"), body.get("motDePasse")));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<Citoyen>> getUsers() {
        return ResponseEntity.ok(authService.getTousLesUtilisateurs());
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> body) {
      try {
         return ResponseEntity.ok(authService.envoyerCode(body.get("email")));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> body) {
       try {
         return ResponseEntity.ok(authService.resetMotDePasse(body.get("email"),body.get("code"),body.get("newPassword")));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
