package com.formlaez.infrastructure.repository;

import com.formlaez.infrastructure.model.entity.form.JpaFormField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaFormFieldRepository extends JpaRepository<JpaFormField, Long> {

    Optional<JpaFormField> findByCode(String fieldCode);

    @Modifying
    @Query("update JpaFormField o set o.position = o.position + :value" +
            " where" +
            " o.section.id = :sectionId" +
            " and (:fromPosition is null or o.position >= :fromPosition)" +
            " and (:toPosition is null or o.position <= :toPosition)")
    void changePositionInRange(@Param("sectionId") Long sectionId,
                               @Param("value") int value,
                               @Param("fromPosition") Integer fromPosition,
                               @Param("toPosition") Integer toPosition);
}