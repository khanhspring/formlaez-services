package com.formlaez.infrastructure.repository;

import com.formlaez.infrastructure.model.entity.JpaAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaAttachmentRepository extends JpaRepository<JpaAttachment, Long> {

    Optional<JpaAttachment> findByCode(String code);
}