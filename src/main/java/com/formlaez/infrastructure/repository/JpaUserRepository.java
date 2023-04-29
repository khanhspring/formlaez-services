package com.formlaez.infrastructure.repository;

import com.formlaez.application.model.request.SearchUserRequest;
import com.formlaez.infrastructure.model.entity.JpaUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaUserRepository extends JpaRepository<JpaUser, String> {
    @Query("select o from JpaUser o" +
            " where 1=1" +
            " and o.status = com.formlaez.infrastructure.enumeration.UserStatus.Active" +
            " and (:#{#request.keyword == null} = true or o.firstName like %:#{#request.keyword}% or o.lastName like %:#{#request.keyword}%)" +
            " and (:#{#request.email == null} = true or o.email like %:#{#request.email}%)")
    Page<JpaUser> search(@Param("request") SearchUserRequest request, Pageable pageable);

    boolean existsByEmail(String email);
}