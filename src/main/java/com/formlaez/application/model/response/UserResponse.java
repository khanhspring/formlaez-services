package com.formlaez.application.model.response;

import com.formlaez.infrastructure.enumeration.UserStatus;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;

    private UserStatus status;
}
