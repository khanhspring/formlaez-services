package com.formlaez.infrastructure.repository;

import com.formlaez.infrastructure.model.entity.pageview.JpaPageViewListingField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaPageViewListingFieldRepository extends JpaRepository<JpaPageViewListingField, Long> {

    @Modifying
    @Query("delete from JpaPageViewListingField o where o.pageView.id = :pageViewId")
    void deleteByPageViewId(@Param("pageViewId") Long pageViewId);
}