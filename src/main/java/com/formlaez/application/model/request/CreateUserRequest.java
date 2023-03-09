package com.formlaez.application.model.request;

import com.formlaez.infrastructure.enumeration.UserStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateUserRequest {
    private UUID id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private UserStatus status;
}
