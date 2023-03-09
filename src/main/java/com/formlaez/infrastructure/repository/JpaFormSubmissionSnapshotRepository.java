package com.formlaez.infrastructure.repository;

import com.formlaez.infrastructure.model.entity.form.JpaFormSubmissionSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaFormSubmissionSnapshotRepository extends JpaRepository<JpaFormSubmissionSnapshot, Long> {

}