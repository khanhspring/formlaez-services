package com.formlaez.application.api.admin;

import com.formlaez.application.model.request.*;
import com.formlaez.application.model.response.ResponseId;
import com.formlaez.application.model.response.WorkspaceMemberResponse;
import com.formlaez.application.model.response.WorkspaceResponse;
import com.formlaez.service.admin.workspace.WorkspaceAdminService;
import com.formlaez.service.admin.workspace.WorkspaceMemberAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/admin/workspaces")
public class WorkspaceAdminController {

    private final WorkspaceAdminService workspaceAdminService;
    private final WorkspaceMemberAdminService workspaceMemberAdminService;

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

    @GetMapping("{workspaceId}/members")
    public Page<WorkspaceMemberResponse> getMembers(@PathVariable Long workspaceId,
                                                    SearchWorkspaceMemberRequest request,
                                                    @PageableDefault(size = 20, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        request.setWorkspaceId(workspaceId);
        return workspaceMemberAdminService.search(request, pageable);
    }

    @PostMapping("{workspaceId}/members")
    public ResponseId<Long> addMember(@PathVariable Long workspaceId,
                                      @RequestBody @Valid AddWorkspaceMemberRequest request) {
        request.setWorkspaceId(workspaceId);
        return ResponseId.of(workspaceMemberAdminService.add(request));
    }

    @DeleteMapping("{workspaceId}/members/{userId}")
    public void removeMember(@PathVariable Long workspaceId,
                             @PathVariable String userId) {
        var request = RemoveWorkspaceMemberRequest.builder()
                .userId(userId)
                .workspaceId(workspaceId)
                .build();
        workspaceMemberAdminService.remove(request);
    }

    @PutMapping("{workspaceId}/members/{userId}")
    public void updateRole(@PathVariable Long workspaceId,
                           @PathVariable String userId,
                           @RequestBody @Valid UpdateWorkspaceMemberRoleRequest request) {
        request.setWorkspaceId(workspaceId);
        request.setUserId(userId);
        workspaceMemberAdminService.updateRole(request);
    }
}
