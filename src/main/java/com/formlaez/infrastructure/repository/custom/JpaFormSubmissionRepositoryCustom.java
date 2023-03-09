package com.formlaez.infrastructure.repository.custom;

import com.formlaez.application.model.request.AdvanceSearchFormSubmissionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface JpaFormSubmissionRepositoryCustom {

    Page<JpaFormSubmissionSearchResult> searchAdvance(@Param("request") AdvanceSearchFormSubmissionRequest request, Pageable pageable);

}