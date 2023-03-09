package com.formlaez.service.user;

import com.formlaez.application.model.request.CreateUserRequest;

import java.util.UUID;

public interface UserService {
    UUID create(CreateUserRequest request);
}
