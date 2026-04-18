package com.transport.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;



@Data
@Entity
@Table(name="transport_data")
public class Transport {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String typeTransport;
	private String frequence;
	private String zone;
	private String heure;
	private String difficultes;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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

}
