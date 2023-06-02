package com.formlaez.service.usage.impl;

import com.formlaez.application.model.response.WorkspaceUsageStatisticResponse;
import com.formlaez.infrastructure.configuration.exception.UsageLimitExceededException;
import com.formlaez.infrastructure.model.entity.JpaWorkspaceMonthlyUsage;
import com.formlaez.infrastructure.model.entity.JpaWorkspaceUsage;
import com.formlaez.infrastructure.property.PlansProperties;
import com.formlaez.infrastructure.repository.JpaWorkspaceMonthlyUsageRepository;
import com.formlaez.infrastructure.repository.JpaWorkspaceRepository;
import com.formlaez.infrastructure.repository.JpaWorkspaceUsageRepository;
import com.formlaez.service.usage.WorkspaceUsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class WorkspaceUsageServiceImpl implements WorkspaceUsageService {

    private final JpaWorkspaceMonthlyUsageRepository jpaWorkspaceMonthlyUsageRepository;
    private final JpaWorkspaceUsageRepository jpaWorkspaceUsageRepository;
    private final JpaWorkspaceRepository jpaWorkspaceRepository;
    private final PlansProperties plansProperties;

    @Override
    @Transactional
    public void increaseForm(Long workspaceId) {
        var usageExists = jpaWorkspaceUsageRepository.existsByWorkspaceId(workspaceId);
        if (usageExists) {
            jpaWorkspaceUsageRepository.increaseForm(workspaceId);
            return;
        }
        var workspace = jpaWorkspaceRepository.findById(workspaceId)
                .orElseThrow();
        var usage = JpaWorkspaceUsage.builder()
                .workspace(workspace)
                .totalForm(1)
                .build();
        jpaWorkspaceUsageRepository.save(usage);
    }

    @Override
    @Transactional
    public void increaseMember(Long workspaceId) {
        var usageExists = jpaWorkspaceUsageRepository.existsByWorkspaceId(workspaceId);
        if (usageExists) {
            jpaWorkspaceUsageRepository.increaseMember(workspaceId);
            return;
        }
        var workspace = jpaWorkspaceRepository.findById(workspaceId)
                .orElseThrow();
        var usage = JpaWorkspaceUsage.builder()
                .workspace(workspace)
                .totalMember(1)
                .build();
        jpaWorkspaceUsageRepository.save(usage);
    }

    @Override
    @Transactional
    public void increaseFileStorage(Long workspaceId, long value) {
        var usageExists = jpaWorkspaceUsageRepository.existsByWorkspaceId(workspaceId);
        if (usageExists) {
            jpaWorkspaceUsageRepository.increaseFileStorage(workspaceId, value);
            return;
        }
        var workspace = jpaWorkspaceRepository.findById(workspaceId)
                .orElseThrow();
        var usage = JpaWorkspaceUsage.builder()
                .workspace(workspace)
                .totalFileStorage(value)
                .build();
        jpaWorkspaceUsageRepository.save(usage);
    }

    @Override
    @Transactional
    public void increaseSubmission(Long workspaceId) {
        var month = currentMonth();
        var usageExists = jpaWorkspaceMonthlyUsageRepository.existsByWorkspaceIdAndMonth(workspaceId, month);
        if (usageExists) {
            jpaWorkspaceMonthlyUsageRepository.increaseSubmission(workspaceId, month);
            return;
        }
        var workspace = jpaWorkspaceRepository.findById(workspaceId)
                .orElseThrow();
        var monthlyUsage = JpaWorkspaceMonthlyUsage.builder()
                .workspace(workspace)
                .month(month)
                .totalSubmission(1)
                .build();
        jpaWorkspaceMonthlyUsageRepository.save(monthlyUsage);
    }

    @Override
    @Transactional
    public void increaseDocumentMerge(Long workspaceId) {
        var month = currentMonth();
        var usageExists = jpaWorkspaceMonthlyUsageRepository.existsByWorkspaceIdAndMonth(workspaceId, month);
        if (usageExists) {
            jpaWorkspaceMonthlyUsageRepository.increaseDocumentMerge(workspaceId, month);
            return;
        }
        var workspace = jpaWorkspaceRepository.findById(workspaceId)
                .orElseThrow();
        var monthlyUsage = JpaWorkspaceMonthlyUsage.builder()
                .workspace(workspace)
                .month(month)
                .totalDocumentMerge(1)
                .build();
        jpaWorkspaceMonthlyUsageRepository.save(monthlyUsage);
    }

    @Override
    @Transactional
    public void decreaseForm(Long workspaceId) {
        var usageExists = jpaWorkspaceUsageRepository.existsByWorkspaceId(workspaceId);
        if (usageExists) {
            jpaWorkspaceUsageRepository.decreaseForm(workspaceId);
        }
    }

    @Override
    @Transactional
    public void decreaseMember(Long workspaceId) {
        var usageExists = jpaWorkspaceUsageRepository.existsByWorkspaceId(workspaceId);
        if (usageExists) {
            jpaWorkspaceUsageRepository.decreaseMember(workspaceId);
        }
    }

    @Override
    @Transactional
    public void decreaseFileStorage(Long workspaceId, long value) {
        var usageExists = jpaWorkspaceUsageRepository.existsByWorkspaceId(workspaceId);
        if (usageExists) {
            jpaWorkspaceUsageRepository.decreaseFileStorage(workspaceId, value);
        }
    }

    @Override
    @Transactional
    public void decreaseSubmission(Long workspaceId) {
        var month = currentMonth();
        var usageExists = jpaWorkspaceMonthlyUsageRepository.existsByWorkspaceIdAndMonth(workspaceId, month);
        if (usageExists) {
            jpaWorkspaceMonthlyUsageRepository.decreaseSubmission(workspaceId, month);
        }
    }

    @Override
    @Transactional
    public void decreaseDocumentMerge(Long workspaceId) {
        var month = currentMonth();
        var usageExists = jpaWorkspaceMonthlyUsageRepository.existsByWorkspaceIdAndMonth(workspaceId, month);
        if (usageExists) {
            jpaWorkspaceMonthlyUsageRepository.decreaseDocumentMerge(workspaceId, month);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExceededFormLimit(Long workspaceId) {
        var workspace = jpaWorkspaceRepository.findById(workspaceId)
                .orElseThrow();
        var usage = jpaWorkspaceUsageRepository.findByWorkspaceId(workspaceId)
                .orElse(new JpaWorkspaceUsage());
        var plan = plansProperties.getPlan(workspace.getType());
        return plan.getFormLimit() <= usage.getTotalForm();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExceededMemberLimit(Long workspaceId) {
        var workspace = jpaWorkspaceRepository.findById(workspaceId)
                .orElseThrow();
        var usage = jpaWorkspaceUsageRepository.findByWorkspaceId(workspaceId)
                .orElse(JpaWorkspaceUsage.builder()
                        .totalMember(1)
                        .build());
        var plan = plansProperties.getPlan(workspace.getType());
        return plan.getWorkspaceMember() <= usage.getTotalMember();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExceededFileStorageLimit(Long workspaceId) {
        var workspace = jpaWorkspaceRepository.findById(workspaceId)
                .orElseThrow();
        var usage = jpaWorkspaceUsageRepository.findByWorkspaceId(workspaceId)
                .orElse(new JpaWorkspaceUsage());
        var plan = plansProperties.getPlan(workspace.getType());
        return plan.getFileStorageLimit() <= usage.getTotalFileStorage();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExceededSubmissionLimit(Long workspaceId) {
        var workspace = jpaWorkspaceRepository.findById(workspaceId)
                .orElseThrow();
        var month = currentMonth();
        var usage = jpaWorkspaceMonthlyUsageRepository.findByWorkspaceIdAndMonth(workspaceId, month)
                .orElse(new JpaWorkspaceMonthlyUsage());
        var plan = plansProperties.getPlan(workspace.getType());
        return plan.getSubmissionPerMonth() <= usage.getTotalSubmission();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isExceededDocumentMerge(Long workspaceId) {
        var workspace = jpaWorkspaceRepository.findById(workspaceId)
                .orElseThrow();
        var month = currentMonth();
        var usage = jpaWorkspaceMonthlyUsageRepository.findByWorkspaceIdAndMonth(workspaceId, month)
                .orElse(new JpaWorkspaceMonthlyUsage());
        var plan = plansProperties.getPlan(workspace.getType());
        return plan.getDocumentMergePerMonth() <= usage.getTotalDocumentMerge();
    }

    @Override
    @Transactional(readOnly = true)
    public WorkspaceUsageStatisticResponse getCurrentUsage(Long workspaceId) {
        var month = currentMonth();
        var monthlyUsage = jpaWorkspaceMonthlyUsageRepository.findByWorkspaceIdAndMonth(workspaceId, month)
                .orElse(new JpaWorkspaceMonthlyUsage());
        var usage = jpaWorkspaceUsageRepository.findByWorkspaceId(workspaceId)
                .orElse(JpaWorkspaceUsage.builder()
                        .totalMember(1)
                        .build());

        var totalMember = 1;
        if (usage.getTotalMember() > 0) {
            totalMember = usage.getTotalMember();
        }

        return WorkspaceUsageStatisticResponse.builder()
                .totalForm(usage.getTotalForm())
                .totalMember(totalMember)
                .totalFileStorage(usage.getTotalFileStorage())
                .totalSubmission(monthlyUsage.getTotalSubmission())
                .totalDocumentMerge(monthlyUsage.getTotalDocumentMerge())
                .build();
    }

    @Override
    @Transactional
    public void checkFormLimitAndIncreaseOrElseThrow(Long workspaceId) {
        if (isExceededFormLimit(workspaceId)) {
            throw new UsageLimitExceededException();
        }
        increaseForm(workspaceId);
    }

    @Override
    @Transactional
    public void checkMemberLimitAndIncreaseOrElseThrow(Long workspaceId) {
        if (isExceededMemberLimit(workspaceId)) {
            throw new UsageLimitExceededException();
        }
        increaseMember(workspaceId);
    }

    @Override
    @Transactional
    public void checkFileStorageLimitAndIncreaseOrElseThrow(Long workspaceId, long value) {
        if (isExceededFileStorageLimit(workspaceId)) {
            throw new UsageLimitExceededException();
        }
        increaseFileStorage(workspaceId, value);
    }

    @Override
    @Transactional
    public void checkSubmissionLimitAndIncreaseOrElseThrow(Long workspaceId) {
        if (isExceededSubmissionLimit(workspaceId)) {
            throw new UsageLimitExceededException();
        }
        increaseSubmission(workspaceId);
    }

    @Override
    @Transactional
    public void checkDocumentMergeLimitAndIncreaseOrElseThrow(Long workspaceId) {
        if (isExceededDocumentMerge(workspaceId)) {
            throw new UsageLimitExceededException();
        }
        increaseDocumentMerge(workspaceId);
    }

    private int currentMonth() {
        var monthStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyDD"));
        return Integer.parseInt(monthStr);
    }
}
