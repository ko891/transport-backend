package com.transport.backend.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.transport.backend.model.Citoyen;
import com.transport.backend.model.VerificationCode;
import com.transport.backend.repository.CitoyenRepository;
import com.transport.backend.repository.VerificationCodeRepository;

@Service
public class AuthService {
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
	
	public String envoyerCode(String email) {
		if (citoyenRepository.findByEmail(email).isPresent()) {
			throw new RuntimeException("Cet email est déja utilisé");
		}
		String code = String.format("%06d", new Random().nextInt(999999));
		
		VerificationCode vc = new VerificationCode();
		vc.setEmail(email);
		vc.setCode(code);
		verificationCodeRepository.save(vc);
		
		emailService.envoyerCode(email, code);
		return "Code envoyé avec succès";	
	}
	//Vérifier le code
	public String verifierCode(String email, String code) {
		VerificationCode vc = verificationCodeRepository.findByEmailAndCode(email, code).orElseThrow(() -> new RuntimeException("Code invalide"));
		
		if (vc.getExpiration().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("Code expiré");
		}
		verificationCodeRepository.delete(vc);
		return "Code vérifié avec succès";
	}
	//Inscription
	public Citoyen inscrire(Citoyen citoyen) {
		citoyen.setMotDePasse(passwordEncoder.encode(citoyen.getMotDePasse()));
		return citoyenRepository.save(citoyen);
	}
    //Connexion
	public Map<String, Object> connecter(String email, String motDePasse){
		Citoyen citoyen = citoyenRepository.findByEmail(email).filter(c -> passwordEncoder.matches(motDePasse, c.getMotDePasse())).orElseThrow(() -> new RuntimeException("Email ou mot de passe incorrect"));
		
		String token = jwtService.genererToken(citoyen.getEmail(), citoyen.getRole());
		
		Map<String, Object> response = new HashMap<>();
		response.put("token", token);
		response.put("id", citoyen.getId());
		response.put("nom", citoyen.getNom());
		response.put("prenom", citoyen.getPrenom());
		response.put("email", citoyen.getEmail());
		response.put("role", citoyen.getRole());
		return response ;
	}
	
	//Liste utilisateurs
	public List<Citoyen>getTousLesUtilisateurs(){
		return citoyenRepository.findAll();
	}
	
	//Mot de passe oublié : envoyer code
	public String envoyerCodeRest(String email) {
		if (!citoyenRepository.findByEmail(email).isPresent()) {
			throw new RuntimeException("Email introuvable");
		}
		
		String code = String.format("%06d", new Random().nextInt(999999));
		verificationCodeRepository.deleteByEmail(email);
		
		VerificationCode vc = new VerificationCode();
		vc.setEmail(email);
		vc.setCode(code);
		verificationCodeRepository.save(vc);
		
		emailService.envoyerCode(email, code);
		return "Code de réinitialisation envoyé";
	}
	
	 //Mot de passe oublié : réinitialiser
	public String resetMotDePasse(String email, String code, String newPassword) {
		VerificationCode vc = verificationCodeRepository.findByEmailAndCode(email, code).orElseThrow(() -> new RuntimeException("Code invalide"));
		
		if (vc.getExpiration().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("Cose expiré");
		}
		Citoyen citoyen = citoyenRepository.findByEmail(email).get();
		citoyen.setMotDePasse(passwordEncoder.encode(newPassword));
		citoyenRepository.save(citoyen);
		
		verificationCodeRepository.delete(vc);
		return "Mot de passe réinitialisé avec succès";
	}
}
