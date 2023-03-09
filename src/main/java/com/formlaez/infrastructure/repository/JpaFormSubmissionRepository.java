package com.formlaez.infrastructure.repository;

import com.formlaez.application.model.request.SearchFormSubmissionRequest;
import com.formlaez.infrastructure.model.entity.form.JpaFormSubmission;
import com.formlaez.infrastructure.model.projection.JpaFormSubmissionProjection;
import com.formlaez.infrastructure.repository.custom.JpaFormSubmissionRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaFormSubmissionRepository extends JpaRepository<JpaFormSubmission, Long>, JpaFormSubmissionRepositoryCustom {

    @Query("select o as formSubmission, c as createdBy, m as lastModifiedBy" +
            " from JpaFormSubmission o" +
            " left join JpaUser c on c.id = o.createdBy" +
            " left join JpaUser m on m.id = o.lastModifiedBy" +
            " where 1=1" +
            " and (:#{#request.formCode == null} = true or o.form.code = :#{#request.formCode})" +
            " and (:#{#request.fromDate == null} = true or o.createdDate >= :#{#request.fromDate})" +
            " and (:#{#request.toDate == null} = true or o.createdDate <= :#{#request.toDate})")
    Page<JpaFormSubmissionProjection> search(@Param("request") SearchFormSubmissionRequest request, Pageable pageable);
}