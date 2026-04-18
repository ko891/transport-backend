package com.transport.backend.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.transport.backend.model.VerificationCode;


public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long>{

	Optional<VerificationCode> findByEmailAndCode(String email, String code);
	
	void deleteByEmail(String email);
}
