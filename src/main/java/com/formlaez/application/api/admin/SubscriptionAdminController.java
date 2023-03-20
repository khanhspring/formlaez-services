package com.formlaez.application.api.admin;

import com.formlaez.application.model.response.SubscriptionResponse;
import com.formlaez.application.model.response.UserSessionResponse;
import com.formlaez.service.subscription.SubscriptionService;
import com.formlaez.service.user.UserSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/admin/workspaces/{workspaceId}/subscription")
public class SubscriptionAdminController {

    private final SubscriptionService subscriptionService;

    @GetMapping
    public SubscriptionResponse getCurrentSubscription(@PathVariable Long workspaceId) {
        return subscriptionService.getCurrent(workspaceId);
    }
}
