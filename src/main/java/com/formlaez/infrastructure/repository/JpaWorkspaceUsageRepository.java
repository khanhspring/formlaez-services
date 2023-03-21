package com.formlaez.infrastructure.repository;

import com.formlaez.infrastructure.model.entity.JpaWorkspaceUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JpaWorkspaceUsageRepository extends JpaRepository<JpaWorkspaceUsage, Long> {

    boolean existsByWorkspaceId(Long workspaceId);
    Optional<JpaWorkspaceUsage> findByWorkspaceId(Long workspaceId);

    @Modifying
    @Query("update JpaWorkspaceUsage o" +
            " set o.totalForm = o.totalForm + 1" +
            " where o.workspace.id = :workspaceId")
    void increaseForm(Long workspaceId);

    @Modifying
    @Query("update JpaWorkspaceUsage o" +
            " set o.totalForm = o.totalForm - 1" +
            " where o.workspace.id = :workspaceId")
    void decreaseForm(Long workspaceId);

    @Modifying
    @Query("update JpaWorkspaceUsage o" +
            " set o.totalMember = o.totalMember + 1" +
            " where o.workspace.id = :workspaceId")
    void increaseMember(Long workspaceId);

    @Modifying
    @Query("update JpaWorkspaceUsage o" +
            " set o.totalMember = o.totalMember - 1" +
            " where o.workspace.id = :workspaceId")
    void decreaseMember(Long workspaceId);

    @Modifying
    @Query("update JpaWorkspaceUsage o" +
            " set o.totalFileStorage = o.totalFileStorage + :value" +
            " where o.workspace.id = :workspaceId")
    void increaseFileStorage(Long workspaceId, long value);

    @Modifying
    @Query("update JpaWorkspaceUsage o" +
            " set o.totalFileStorage = CASE WHEN o.totalFileStorage - :value > 0 THEN o.totalFileStorage - :value ELSE 0 END" +
            " where o.workspace.id = :workspaceId")
    void decreaseFileStorage(Long workspaceId, long value);
}