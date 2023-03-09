package com.formlaez.application.model.response;

import com.formlaez.infrastructure.enumeration.UserStatus;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;

    private UserStatus status;
}
