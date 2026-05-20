package com.transport.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transport.backend.model.Transport;
import com.transport.backend.repository.TransportRepository;

  //Contrôleur REST pour la gestion des formulaires de transport
  //Gère : ajout d'un formulaire, récupération de tous les formulaires
  //URL de base : /api/transport
 
@RestController
@RequestMapping("/api/transport")
@CrossOrigin(origins="*",allowedHeaders ="*")
public class TransportController {
	
	@Autowired
	private TransportRepository transportRepository;
	
    // POST /api/transport
    // Reçoit un formulaire de transport depuis Angular
    // et le sauvegarde dans la table "transports" de PostgreSQL
     
	@PostMapping
	public ResponseEntity<?>ajouter(
			@RequestBody Transport transport){
		try {
			// Sauvegarde le formulaire dans PostgreSQL
			Transport saved = transportRepository.save(transport);
			return ResponseEntity.ok(saved);
		}
		catch (Exception e) {
			return ResponseEntity.badRequest().body("erreur:"+e.getMessage());
		}
	}
	
     // GET /api/transport
     // Retourne la liste de tous les formulaires de transport
     // Utilisé par le Dashboard Admin et la page "Mes Données"
  
     @GetMapping
     public ResponseEntity<List<Transport>>getAll(){
    	 return ResponseEntity.ok(transportRepository.findAll());
     }
     
       //GET /api/transport/user/{userId}
      //Retourne uniquement les formulaires de l'utilisateur connecté
    
     @GetMapping("/user/{userId}")
     public ResponseEntity<List<Transport>> getByUser(
             @PathVariable Long userId) {
         return ResponseEntity.ok(transportRepository.findByUserId(userId));
     }
}
