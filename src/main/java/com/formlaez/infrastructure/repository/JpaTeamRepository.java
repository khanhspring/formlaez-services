package com.formlaez.infrastructure.repository;

import com.formlaez.application.model.request.SearchFormRequest;
import com.formlaez.application.model.request.SearchTeamRequest;
import com.formlaez.infrastructure.model.entity.form.JpaForm;
import com.formlaez.infrastructure.model.entity.team.JpaTeam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JpaTeamRepository extends JpaRepository<JpaTeam, Long> {
    Optional<JpaTeam> findByCode(String code);

    @Query("select o from JpaTeam o" +
            " where 1=1" +
            " and o.workspace.id = :#{#request.workspaceId}" +
            " and (:#{#request.keyword == null} = true or o.name like %:#{#request.keyword}% or o.description like %:#{#request.keyword}%)")
    Page<JpaTeam> search(@Param("request") SearchTeamRequest request, Pageable pageable);
}