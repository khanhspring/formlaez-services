package com.formlaez.infrastructure.client;

import com.formlaez.infrastructure.client.model.PaddleResponse;
import com.formlaez.infrastructure.client.model.PaddleUpdateUserResponse;

public interface PaddleClient {
    PaddleResponse<PaddleUpdateUserResponse> updateUser(String subscriptionId, String planId);
}
