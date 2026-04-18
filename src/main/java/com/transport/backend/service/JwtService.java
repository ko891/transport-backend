package com.transport.backend.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
       //Clé secrète pour signer le token
       private final Key SECRT_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
       
       //Durée de validité : 24 heures
       private final Long EXPIRATION = 1000l * 60 * 60 * 24;
       
       //Générer un token JWT
       public  String genererToken(String email , String role) {
    	   Map<String, Object> claims = new HashMap<>();
    	   claims.put("role", role);
    	   return Jwts.builder().setClaims(claims).setSubject(email).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis()+EXPIRATION)).signWith(SECRT_KEY).compact();
       }
       
       // Extraire l'email du token
       public String extraireEmail(String token) {
    	   return Jwts.parserBuilder().setSigningKey(SECRT_KEY).build().parseClaimsJwt(token).getBody().getSubject();  
       }
       
       //Extraire le rôle du token
       public String extraireRole(String token) {
    	   return (String)Jwts.parserBuilder().setSigningKey(SECRT_KEY).build().parseClaimsJws(token).getBody().get("role");
       }
       
       //Extraire le rôle du token
       public Boolean estValide(String token) {
    	   try {
    		   Jwts.parserBuilder().setSigningKey(SECRT_KEY).build().parseClaimsJws(token);
    		   return true;
    	   }catch (Exception e) {
    		   return false;
    	   }
       }
}
