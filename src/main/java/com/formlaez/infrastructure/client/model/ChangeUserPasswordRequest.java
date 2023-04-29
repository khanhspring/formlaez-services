package com.formlaez.infrastructure.client.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeUserPasswordRequest {
    private String userId;
    private String currentPassword;
    private String newPassword;
}
