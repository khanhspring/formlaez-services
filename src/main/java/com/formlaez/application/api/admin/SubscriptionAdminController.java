package com.formlaez.application.api.admin;

import com.formlaez.application.model.request.ChangeWorkspacePlanRequest;
import com.formlaez.application.model.response.SubscriptionResponse;
import com.formlaez.service.subscription.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/admin/workspaces/{workspaceId}/subscription")
public class SubscriptionAdminController {

    private final SubscriptionService subscriptionService;

    @GetMapping
    public SubscriptionResponse getCurrentSubscription(@PathVariable Long workspaceId) {
        return subscriptionService.getCurrent(workspaceId);
    }

    @PutMapping("plan")
    public void changePlan(@PathVariable Long workspaceId,
                           @RequestBody @Valid ChangeWorkspacePlanRequest request) {
        request.setWorkspaceId(workspaceId);
        subscriptionService.changePlan(request);
    }
}
