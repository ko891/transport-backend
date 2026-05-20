package com.transport.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="transport_data")
public class Transport {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="user_id")
	private Long userId;
	
	private String typeTransport;
	private String frequence;
	private String zone;
	private String heure;
	private String difficultes;
	private String profil;           
	private String depart;           
	private String destination;      
	private String heureRetour;      
	private Integer satisfaction;    
	private String propositions;     
	private String lieu; 
	private String lieuDepart;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getTypeTransport() {
		return typeTransport;
	}
	public void setTypeTransport(String typeTransport) {
		this.typeTransport = typeTransport;
	}
	public String getFrequence() {
		return frequence;
	}
	public void setFrequence(String frequence) {
		this.frequence = frequence;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getHeure() {
		return heure;
	}
	public void setHeure(String heure) {
		this.heure = heure;
	}
	public String getDifficultes() {
		return difficultes;
	}
	public void setDifficultes(String difficultes) {
		this.difficultes = difficultes;
	}
	public String getProfil() {
		 return profil; 
		 }
    public void setProfil(String profil) {
    	this.profil = profil;
    	}
	public String getDepart() { 
		return depart; 
		}
	public void setDepart(String depart) {
		this.depart = depart; 
		}
	public String getDestination() {
		return destination; 
		}
	public void setDestination(String destination) {
		this.destination = destination; 
		}
	public String getHeureRetour() {
		return heureRetour; 
		}
	public void setHeureRetour(String heureRetour) {
		this.heureRetour = heureRetour;
		}
	public Integer getSatisfaction() {
		return satisfaction;
		}
	public void setSatisfaction(Integer satisfaction) { 
		this.satisfaction = satisfaction; 
		}
	public String getPropositions() {
		return propositions; 
		}
	public void setPropositions(String propositions) {
		this.propositions = propositions; 
		}
	public String getLieu() { 
		return lieu;
		}
	public void setLieu(String lieu) { 
		this.lieu = lieu;
		}
	public String getLieuDepart() {
		return lieuDepart;
	}
	public void setLieuDepart(String lieuDepart) {
		this.lieuDepart = lieuDepart;
	}
	public String getLieuDestination() {
		return lieuDestination;
	}
	public void setLieuDestination(String lieuDestination) {
		this.lieuDestination = lieuDestination;
	}
	private String lieuDestination;
}
