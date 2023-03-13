package com.formlaez.service.internal.user;

import com.formlaez.application.model.request.CreateUserRequest;

import java.util.UUID;

public interface UserInternalService {
    UUID create(CreateUserRequest request);
}
