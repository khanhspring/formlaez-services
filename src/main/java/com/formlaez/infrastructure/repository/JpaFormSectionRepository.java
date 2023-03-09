package com.formlaez.infrastructure.repository;

import com.formlaez.infrastructure.model.entity.form.JpaFormSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaFormSectionRepository extends JpaRepository<JpaFormSection, Long> {

    Optional<JpaFormSection> findByCode(String code);

    @Modifying
    @Query("update JpaFormSection o set o.position = o.position + :value" +
            " where" +
            " o.page.id = :formPageId" +
            " and (:fromPosition is null or o.position >= :fromPosition)" +
            " and (:toPosition is null or o.position <= :toPosition)")
    void changePositionInRange(@Param("formPageId") Long formPageId,
                               @Param("value") int value,
                               @Param("fromPosition") Integer fromPosition,
                               @Param("toPosition") Integer toPosition);
}