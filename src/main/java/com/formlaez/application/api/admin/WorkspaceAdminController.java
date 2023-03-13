package com.formlaez.application.api.admin;

import com.formlaez.application.model.request.CreateWorkspaceRequest;
import com.formlaez.application.model.request.UpdateWorkspaceRequest;
import com.formlaez.application.model.response.ResponseId;
import com.formlaez.application.model.response.WorkspaceResponse;
import com.formlaez.service.admin.workspace.WorkspaceAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/admin/workspaces")
public class WorkspaceAdminController {

    private final WorkspaceAdminService workspaceAdminService;

    @PostMapping
    public ResponseId<Long> create(@Valid @RequestBody CreateWorkspaceRequest request) {
        return ResponseId.of(workspaceAdminService.create(request));
    }

    @PutMapping("{workspaceId}")
    public void update(@PathVariable Long workspaceId,
                       @Valid @RequestBody UpdateWorkspaceRequest request) {
        request.setId(workspaceId);
        workspaceAdminService.update(request);
    }

    @GetMapping("{workspaceCode}")
    public WorkspaceResponse getByCode(@PathVariable String workspaceCode) {
        return workspaceAdminService.getByCode(workspaceCode);
    }
}
