package com.formlaez.infrastructure.repository;

import com.formlaez.infrastructure.model.entity.JpaWorkspace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaWorkspaceRepository extends JpaRepository<JpaWorkspace, Long> {

    Optional<JpaWorkspace> findByCode(String code);
}