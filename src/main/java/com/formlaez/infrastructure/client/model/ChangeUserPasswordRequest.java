package com.formlaez.infrastructure.client.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeUserPasswordRequest {
    private UUID userId;
    private String currentPassword;
    private String newPassword;
}
