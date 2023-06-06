package com.formlaez.infrastructure.repository;

import com.formlaez.infrastructure.model.entity.pageview.JpaPageViewDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaPageViewDetailRepository extends JpaRepository<JpaPageViewDetail, Long> {

    @Modifying
    @Query("delete from JpaPageViewDetail o where o.pageView.id = :pageViewId")
    void deleteByPageViewId(@Param("pageViewId") Long pageViewId);
}