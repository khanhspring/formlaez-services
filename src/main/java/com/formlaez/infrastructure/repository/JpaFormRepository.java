package com.formlaez.infrastructure.repository;

import com.formlaez.application.model.request.SearchFormRequest;
import com.formlaez.infrastructure.model.entity.form.JpaForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaFormRepository extends JpaRepository<JpaForm, Long> {

    Optional<JpaForm> findByCode(String code);

    @Query("select o from JpaForm o" +
            " where 1=1" +
            " and (:#{#request.keyword == null} = true or o.title like %:#{#request.keyword}% or o.description like %:#{#request.keyword}%)" +
            " and (:#{#request.scope == null} = true or o.scope = :#{#request.scope})" +
            " and (:#{#request.createdBy == null} = true or o.createdBy = :#{#request.createdBy})")
    Page<JpaForm> search(@Param("request") SearchFormRequest request, Pageable pageable);
}