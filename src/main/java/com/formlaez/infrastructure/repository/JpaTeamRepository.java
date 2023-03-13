package com.formlaez.infrastructure.repository;

import com.formlaez.infrastructure.model.entity.team.JpaTeam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTeamRepository extends JpaRepository<JpaTeam, Long> {

}