package com.formlaez.service.user.impl;

import com.formlaez.application.model.request.ChangePasswordRequest;
import com.formlaez.infrastructure.client.AuthInternalClient;
import com.formlaez.infrastructure.client.model.ChangeUserPasswordRequest;
import com.formlaez.infrastructure.util.AuthUtils;
import com.formlaez.service.user.UserSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSettingsServiceImpl implements UserSettingsService {

    private final AuthInternalClient authInternalClient;

    @Override
    public void changePassword(ChangePasswordRequest request) {
        var currentUserId = AuthUtils.currentUserIdOrElseThrow();
        var changeUserPasswordRequest = ChangeUserPasswordRequest.builder()
                .currentPassword(request.getCurrentPassword())
                .newPassword(request.getNewPassword())
                .userId(currentUserId)
                .build();
        authInternalClient.changeUserPassword(changeUserPasswordRequest);
    }
}
