package com.formlaez.service.admin.workspace;

import com.formlaez.application.model.request.UpdateOpenAIApiSettingRequest;
import com.formlaez.application.model.response.WorkspaceOpenAIApiResponse;

public interface WorkspaceOpenApiSettingAdminService {

    void update(UpdateOpenAIApiSettingRequest request);
    WorkspaceOpenAIApiResponse get(Long workspaceId);
}
