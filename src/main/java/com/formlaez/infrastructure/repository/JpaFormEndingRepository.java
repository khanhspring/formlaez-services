package com.formlaez.infrastructure.repository;

import com.formlaez.infrastructure.model.entity.form.JpaFormEnding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface JpaFormEndingRepository extends JpaRepository<JpaFormEnding, Long> {

    Optional<JpaFormEnding> findByFormId(Long formId);

}