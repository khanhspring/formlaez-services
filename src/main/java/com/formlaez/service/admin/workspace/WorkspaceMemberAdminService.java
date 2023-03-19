package com.formlaez.service.admin.workspace;

import com.formlaez.application.model.request.AddWorkspaceMemberRequest;
import com.formlaez.application.model.request.RemoveWorkspaceMemberRequest;
import com.formlaez.application.model.request.SearchWorkspaceMemberRequest;
import com.formlaez.application.model.request.UpdateWorkspaceMemberRoleRequest;
import com.formlaez.application.model.response.WorkspaceMemberResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WorkspaceMemberAdminService {
    Long add(AddWorkspaceMemberRequest request);

    void remove(RemoveWorkspaceMemberRequest request);

    void updateRole(UpdateWorkspaceMemberRoleRequest request);

    Page<WorkspaceMemberResponse> search(SearchWorkspaceMemberRequest request, Pageable pageable);
}
