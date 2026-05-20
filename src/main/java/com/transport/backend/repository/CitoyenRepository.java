package com.transport.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.backend.model.Citoyen;

public interface CitoyenRepository extends JpaRepository<Citoyen, Long> {

	
     // Recherche un citoyen par son email
     //Spring génère automatiquement la requête SQL 
     //SELECT * FROM utilisateurs WHERE email = ?
     //Optional = peut retourner null sans erreur
     //Utilisé dans le login pour vérifier les identifiants
     

	Optional<Citoyen>findByEmail (String email);
	Optional<Citoyen> findById(Long id);
}
