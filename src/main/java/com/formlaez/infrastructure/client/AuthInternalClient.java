package com.formlaez.infrastructure.client;

import com.formlaez.application.model.response.ResponseId;
import com.formlaez.infrastructure.client.model.ChangeUserPasswordRequest;
import com.formlaez.infrastructure.client.model.CreateUserRequest;

public interface AuthInternalClient {
    ResponseId<String> createUser(CreateUserRequest request);
    void changeUserPassword(ChangeUserPasswordRequest request);
}
