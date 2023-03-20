package com.formlaez.service.subscription;

import com.formlaez.application.model.request.CancelSubscriptionRequest;
import com.formlaez.application.model.request.CreateSubscriptionRequest;
import com.formlaez.application.model.response.SubscriptionResponse;

public interface SubscriptionService {
    void create(CreateSubscriptionRequest request);
    void cancel(CancelSubscriptionRequest request);
    SubscriptionResponse getCurrent(Long workspaceId);
}
