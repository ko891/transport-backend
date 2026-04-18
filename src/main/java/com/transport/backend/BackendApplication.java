package com.transport.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principale de l'application Spring Boot
 * 
 * @SpringBootApplication combine 3 annotations :
 * - @Configuration      → déclare cette classe comme source de configuration
 * - @EnableAutoConfiguration → configure automatiquement Spring selon les dépendances
 * - @ComponentScan      → détecte automatiquement tous les composants (controllers, services...)
 */
@SpringBootApplication
public class BackendApplication {

	/**
     * Point d'entrée de l'application
     * Lance le serveur Tomcat intégré sur le port 8085
     * et initialise la connexion avec PostgreSQL
     */
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
