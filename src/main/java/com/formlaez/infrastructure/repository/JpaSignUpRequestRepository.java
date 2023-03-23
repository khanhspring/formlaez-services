package com.formlaez.infrastructure.repository;

import com.formlaez.infrastructure.model.entity.JpaSignUpRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaSignUpRequestRepository extends JpaRepository<JpaSignUpRequest, Long> {

    Optional<JpaSignUpRequest> findByVerificationCode(String verificationCode);
}