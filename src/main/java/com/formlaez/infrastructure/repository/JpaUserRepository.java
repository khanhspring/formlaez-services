package com.formlaez.infrastructure.repository;

import com.formlaez.infrastructure.model.entity.JpaUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaUserRepository extends JpaRepository<JpaUser, UUID> {

}