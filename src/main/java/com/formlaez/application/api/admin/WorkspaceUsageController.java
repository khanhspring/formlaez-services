package com.formlaez.application.api.admin;

import com.formlaez.application.model.response.WorkspaceUsageStatisticResponse;
import com.formlaez.service.usage.WorkspaceUsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/admin/workspaces/{workspaceId}/usage")
public class WorkspaceUsageController {

    private final WorkspaceUsageService workspaceUsageService;

    @GetMapping
    public WorkspaceUsageStatisticResponse getStatistic(@PathVariable Long workspaceId) {
        return workspaceUsageService.getCurrentUsage(workspaceId);
    }
}
