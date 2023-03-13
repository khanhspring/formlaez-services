package com.formlaez.infrastructure.repository;

import com.formlaez.infrastructure.model.entity.form.JpaFormFieldOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface JpaFormFieldOptionRepository extends JpaRepository<JpaFormFieldOption, Long> {

    @Modifying
    void deleteAllByFieldId(Long fieldId);
}