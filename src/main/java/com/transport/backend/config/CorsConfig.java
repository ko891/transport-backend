package com.transport.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration CORS (Cross-Origin Resource Sharing)
 * 
 * CORS permet à Angular (localhost:4200) de communiquer avec Spring Boot (localhost:8085)
 * Sans cette configuration, le navigateur bloquerait les requêtes entre les 2 serveurs
 * car ils sont sur des ports différents — ce qui est considéré comme "cross-origin"
 */
@Configuration
public class CorsConfig {

	/**
     * Crée et configure le bean WebMvcConfigurer
     * @Bean indique à Spring de gérer cette instance automatiquement
     */
	@Bean
	public WebMvcConfigurer corscConfigurer() {
		return new WebMvcConfigurer() {
			
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:4200").allowedMethods("GET","POST","PUT","DELETE").allowedHeaders("*");
			}
		};
		
		
	}
}
