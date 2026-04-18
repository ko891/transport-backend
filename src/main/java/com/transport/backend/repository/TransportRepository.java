package com.transport.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transport.backend.model.Transport;

public interface TransportRepository extends JpaRepository<Transport, Long> {

}
