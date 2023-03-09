package com.formlaez.service.workspace;

import com.formlaez.application.model.request.CreateWorkspaceRequest;
import com.formlaez.application.model.request.UpdateWorkspaceRequest;
import com.formlaez.application.model.request.UpdateWorkspaceTypeRequest;
import com.formlaez.application.model.response.WorkspaceResponse;

public interface WorkspaceService {
    Long create(CreateWorkspaceRequest request);

    void update(UpdateWorkspaceRequest request);

    void upgrade(UpdateWorkspaceTypeRequest request);

    void downgrade(UpdateWorkspaceTypeRequest request);

    WorkspaceResponse getById(Long id);
}
