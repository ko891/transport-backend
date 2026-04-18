package com.transport.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.backend.model.Reclamation;

public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {

}
