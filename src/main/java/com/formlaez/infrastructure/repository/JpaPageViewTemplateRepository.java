package com.formlaez.infrastructure.repository;

import com.formlaez.infrastructure.model.entity.pageview.JpaPageViewTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaPageViewTemplateRepository extends JpaRepository<JpaPageViewTemplate, Long> {

    Optional<JpaPageViewTemplate> findByCode(String code);
}