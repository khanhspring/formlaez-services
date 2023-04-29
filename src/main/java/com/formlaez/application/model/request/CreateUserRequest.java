package com.formlaez.application.model.request;

import com.formlaez.infrastructure.enumeration.UserStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private UserStatus status;
}
