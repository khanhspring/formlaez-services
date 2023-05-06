package com.formlaez.infrastructure.client.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    private String email;
    private String password;
    private String rawPassword;
    private String firstName;
    private String lastName;
}
