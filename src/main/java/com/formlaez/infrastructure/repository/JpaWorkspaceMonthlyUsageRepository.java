package com.formlaez.infrastructure.repository;

import com.formlaez.infrastructure.model.entity.JpaWorkspaceMonthlyUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JpaWorkspaceMonthlyUsageRepository extends JpaRepository<JpaWorkspaceMonthlyUsage, Long> {

    boolean existsByWorkspaceIdAndMonth(Long workspaceId, int month);
    Optional<JpaWorkspaceMonthlyUsage> findByWorkspaceIdAndMonth(Long workspaceId, int month);

    @Modifying
    @Query("update JpaWorkspaceMonthlyUsage o" +
            " set o.totalSubmission = o.totalSubmission + 1" +
            " where o.workspace.id = :workspaceId and o.month = :month")
    void increaseSubmission(Long workspaceId, int month);

    @Modifying
    @Query("update JpaWorkspaceMonthlyUsage o" +
            " set o.totalSubmission = o.totalSubmission - 1" +
            " where o.workspace.id = :workspaceId and o.month = :month")
    void decreaseSubmission(Long workspaceId, int month);

    @Modifying
    @Query("update JpaWorkspaceMonthlyUsage o" +
            " set o.totalDocumentMerge = o.totalDocumentMerge + 1" +
            " where o.workspace.id = :workspaceId and o.month = :month")
    void increaseDocumentMerge(Long workspaceId, int month);

    @Modifying
    @Query("update JpaWorkspaceMonthlyUsage o" +
            " set o.totalDocumentMerge = o.totalDocumentMerge - 1" +
            " where o.workspace.id = :workspaceId and o.month = :month")
    void decreaseDocumentMerge(Long workspaceId, int month);
}