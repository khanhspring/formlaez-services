package com.formlaez.infrastructure.repository;

import com.formlaez.infrastructure.model.entity.JpaOpenaiApiSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaOpenaiApiSettingRepository extends JpaRepository<JpaOpenaiApiSetting, Long> {

    Optional<JpaOpenaiApiSetting> findByWorkspaceId(Long id);
}