package com.formlaez.infrastructure.repository;

import com.formlaez.infrastructure.model.entity.form.JpaFormFieldOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaFormFieldOptionRepository extends JpaRepository<JpaFormFieldOption, Long> {

}