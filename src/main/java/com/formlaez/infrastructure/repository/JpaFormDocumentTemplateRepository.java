package com.formlaez.infrastructure.repository;

import com.formlaez.application.model.request.SearchFormDocumentTemplateRequest;
import com.formlaez.infrastructure.model.entity.JpaFormDocumentTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaFormDocumentTemplateRepository extends JpaRepository<JpaFormDocumentTemplate, Long> {

    @Query("select o from JpaFormDocumentTemplate o" +
            " where 1=1" +
            " and o.form.id = :#{#request.formId}" +
            " and (:#{#request.keyword == null} = true or o.title like %:#{#request.keyword}% or o.description like %:#{#request.keyword}%)")
    Page<JpaFormDocumentTemplate> search(@Param("request") SearchFormDocumentTemplateRequest request, Pageable pageable);
}