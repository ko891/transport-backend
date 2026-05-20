package com.transport.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.backend.model.Transport;

public interface TransportRepository extends JpaRepository<Transport, Long> {

	List<Transport>findByUserId(Long userId);
}
