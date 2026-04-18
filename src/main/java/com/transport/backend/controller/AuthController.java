package com.transport.backend.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.transport.backend.model.Citoyen;
import com.transport.backend.model.VerificationCode;
import com.transport.backend.repository.CitoyenRepository;
import com.transport.backend.repository.VerificationCodeRepository;
import com.transport.backend.service.EmailService;
import com.transport.backend.service.JwtService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    @Autowired
    private CitoyenRepository citoyenRepository;

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Autowired
    private EmailService emailService;
    
    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Étape 1 : Envoyer le code de vérification
    @PostMapping("/send-code")
    public ResponseEntity<?> sendCode(@RequestBody java.util.Map<String, String> body) {
        try {
            String email = body.get("email");

            if (citoyenRepository.findByEmail(email).isPresent()) {
                return ResponseEntity.badRequest().body("Cet email est déjà utilisé");
            }

            // Format corrigé : %06d pour 6 chiffres
            String code = String.format("%06d", new Random().nextInt(999999));

            verificationCodeRepository.deleteByEmail(email);

            VerificationCode vc = new VerificationCode();
            vc.setEmail(email);
            vc.setCode(code);
            verificationCodeRepository.save(vc);

            emailService.envoyerCode(email, code);
            return ResponseEntity.ok("Code envoyé avec succès");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
        }
    }

    // Étape 2 : Vérifier le code (ENDPOINT MANQUANT — ajouté ici)
    @PostMapping("/verify-code")
    public ResponseEntity<?> verifyCode(@RequestBody java.util.Map<String, String> body) {
        try {
            String email = body.get("email");
            String code = body.get("code");

            VerificationCode vc = verificationCodeRepository
                .findByEmailAndCode(email, code)
                .orElse(null);

            if (vc == null) {
                return ResponseEntity.badRequest().body("Code invalide");
            }

            // Vérification de l'expiration
            if (vc.getExpiration().isBefore(LocalDateTime.now())) {
                return ResponseEntity.badRequest().body("Code expiré");
            }

            verificationCodeRepository.delete(vc);
            return ResponseEntity.ok("Code vérifié avec succès");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
        }
    }

    // Étape 3 : Inscription
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Citoyen citoyen) {
        try {
            // Encoder le mot de passe avant sauvegarde
            String motDePasseEncode = passwordEncoder.encode(citoyen.getMotDePasse());
            citoyen.setMotDePasse(motDePasseEncode);

            Citoyen saved = citoyenRepository.save(citoyen);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
        }
    }
    
    // Connexion avec JWT
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Citoyen citoyen) {
        try {
        	 return citoyenRepository
                     .findByEmail(citoyen.getEmail())
                     //Comparer avec BCrypt au lieu de .equals()
                     .filter(c -> passwordEncoder.matches(
                         citoyen.getMotDePasse(),
                         c.getMotDePasse()
                     ))
                     .map(c -> {
                    	 String token = jwtService.genererToken(c.getEmail(),c.getRole());
                    	 
                    	 Map<String, Object>response = new HashMap<>();
                    	 response.put("token", token);
                    	 response.put("id", c.getId());
                    	 response.put("nom", c.getNom());
                    	 response.put("prenom", c.getPrenom());
                    	 response.put("email", c.getEmail());
                    	 response.put("role", c.getRole());
                    	 return ResponseEntity.ok(response);
                    	 }).orElse(ResponseEntity.badRequest().build());
                     }catch (Exception e) {
                    	 return ResponseEntity.badRequest().body(null);
                     }
         }

       // Liste des utilisateurs (Dashboard Admin)
       @GetMapping("/users")
      public ResponseEntity<List<Citoyen>> getUsers() {
        return ResponseEntity.ok(citoyenRepository.findAll());
       }

       // Mot de passe oublié : envoyer code
       @PostMapping("/forgot-password")
       public ResponseEntity<?> forgotPassword(@RequestBody java.util.Map<String, String> body) {
           try {
               String email = body.get("email");

               if (!citoyenRepository.findByEmail(email).isPresent()) {
                   return ResponseEntity.badRequest().body("Email introuvable");
               }

               String code = String.format("%06d", new Random().nextInt(999999));
               verificationCodeRepository.deleteByEmail(email);

               VerificationCode vc = new VerificationCode();
               vc.setEmail(email);
               vc.setCode(code);
               verificationCodeRepository.save(vc);

               emailService.envoyerCode(email, code);
               return ResponseEntity.ok("Code de réinitialisation envoyé");

           } catch (Exception e) {
               return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
           }
       }

       //Mot de passe oublié : réinitialiser avec BCrypt
       @PostMapping("/reset-password")
       public ResponseEntity<?> resetPassword(@RequestBody java.util.Map<String, String> body) {
           try {
               String email = body.get("email");
               String code = body.get("code");
               String newPassword = body.get("newPassword");

               VerificationCode vc = verificationCodeRepository
                   .findByEmailAndCode(email, code)
                   .orElse(null);

               if (vc == null) {
                   return ResponseEntity.badRequest().body("Code invalide");
               }

               if (vc.getExpiration().isBefore(LocalDateTime.now())) {
                   return ResponseEntity.badRequest().body("Code expiré");
               }

               Citoyen citoyen = citoyenRepository.findByEmail(email).get();
               //Encoder le nouveau mot de passe
               citoyen.setMotDePasse(passwordEncoder.encode(newPassword));
               citoyenRepository.save(citoyen);

               verificationCodeRepository.delete(vc);
               return ResponseEntity.ok("Mot de passe réinitialisé avec succès");

           } catch (Exception e) {
               return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
           }
       }
}
