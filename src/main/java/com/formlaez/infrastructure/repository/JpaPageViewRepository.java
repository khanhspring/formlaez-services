package com.formlaez.infrastructure.repository;

import com.formlaez.infrastructure.enumeration.PageViewStatus;
import com.formlaez.infrastructure.model.entity.pageview.JpaPageView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaPageViewRepository extends JpaRepository<JpaPageView, Long> {

    List<JpaPageView> findAllByFormIdAndStatusInOrderByCreatedDateDesc(Long formId, List<PageViewStatus> statuses);
    Optional<JpaPageView> findByCodeAndStatus(String code, PageViewStatus status);
}