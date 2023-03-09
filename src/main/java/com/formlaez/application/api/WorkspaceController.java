package com.formlaez.application.api;

import com.formlaez.application.model.request.CreateWorkspaceRequest;
import com.formlaez.application.model.request.UpdateWorkspaceRequest;
import com.formlaez.application.model.response.ResponseId;
import com.formlaez.service.workspace.WorkspaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/workspaces")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @PostMapping
    public ResponseId<Long> create(@Valid @RequestBody CreateWorkspaceRequest request) {
        return ResponseId.of(workspaceService.create(request));
    }

    @PutMapping("{workspaceId}")
    public void update(@PathVariable Long workspaceId,
                       @Valid @RequestBody UpdateWorkspaceRequest request) {
        request.setId(workspaceId);
        workspaceService.update(request);
    }
}
