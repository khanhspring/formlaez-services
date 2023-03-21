package com.formlaez.service.usage;

import com.formlaez.application.model.response.WorkspaceUsageStatisticResponse;

public interface WorkspaceUsageService {
    void increaseForm(Long workspaceId);
    void increaseMember(Long workspaceId);
    void increaseFileStorage(Long workspaceId, long value);
    void increaseSubmission(Long workspaceId);
    void increaseDocumentMerge(Long workspaceId);

    void decreaseForm(Long workspaceId);
    void decreaseMember(Long workspaceId);
    void decreaseFileStorage(Long workspaceId, long value);
    void decreaseSubmission(Long workspaceId);
    void decreaseDocumentMerge(Long workspaceId);

    boolean isExceededFormLimit(Long workspaceId);
    boolean isExceededMemberLimit(Long workspaceId);
    boolean isExceededFileStorageLimit(Long workspaceId);
    boolean isExceededSubmissionLimit(Long workspaceId);
    boolean isExceededDocumentMerge(Long workspaceId);

    WorkspaceUsageStatisticResponse getCurrentUsage(Long workspaceId);

    void checkFormLimitAndIncreaseOrElseThrow(Long workspaceId);
    void checkMemberLimitAndIncreaseOrElseThrow(Long workspaceId);
    void checkFileStorageLimitAndIncreaseOrElseThrow(Long workspaceId, long value);
    void checkSubmissionLimitAndIncreaseOrElseThrow(Long workspaceId);
    void checkDocumentMergeLimitAndIncreaseOrElseThrow(Long workspaceId);
}
