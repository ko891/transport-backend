package com.transport.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	public void envoyerCode(String email, String code) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("Code de vérification,Transport Tunis");
		message.setText("Bonjour,\n\n" +
	            "Votre code de vérification est : " + code + "\n\n" +
	            "Ce code est valable pendant 2 minutes.\n\n" +
	            "Si vous n'avez pas demandé ce code, ignorez cet email.\n\n" +
	            "Cordialement,\n" +
	            "L'équipe Transport Tunis");
		mailSender.send(message);
	}
}
