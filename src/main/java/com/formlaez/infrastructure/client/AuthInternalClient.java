package com.formlaez.infrastructure.client;

import com.formlaez.application.model.response.ResponseId;
import com.formlaez.infrastructure.client.model.ChangeUserPasswordRequest;
import com.formlaez.infrastructure.client.model.CreateUserRequest;

import java.util.UUID;

public interface AuthInternalClient {
    ResponseId<UUID> createUser(CreateUserRequest request);
    void changeUserPassword(ChangeUserPasswordRequest request);
}
