package com.transport.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transport.backend.model.Reclamation;
import com.transport.backend.repository.ReclamationRepository;


/**
 * Contrôleur REST pour la gestion des réclamations
 * Gère : ajout et liste des réclamations des citoyens
 * URL de base : /api/reclamations
 */
@RestController
@RequestMapping("/api/reclamations")
@CrossOrigin(origins="*",allowedHeaders="*")
public class ReclamationController {
	
     @Autowired
     private ReclamationRepository reclamationRepository;

  // POST /api/reclamations — Ajouter une nouvelle réclamation
     
     @PostMapping
     public ResponseEntity<?>ajouter(
    		 @RequestBody Reclamation reclamation) {
    	 try {
    		 // Sauvegarde la réclamation dans PostgreSQL
    		 Reclamation saved = reclamationRepository.save(reclamation);
    		 return ResponseEntity.ok(saved);
    	 }
    	 catch (Exception e) {
    		 return ResponseEntity.badRequest().body("Erreur: "+e.getMessage());
    	 }
     }
     
      // GET /api/reclamations — Récupérer toutes les réclamations
     // Utilisé par le Dashboard Admin pour afficher la liste
     @GetMapping
      public ResponseEntity<List<Reclamation>>getAll(){
    	 return ResponseEntity.ok(reclamationRepository.findAll());
     }
}
