package com.formlaez.service.user;

import com.formlaez.application.model.request.ChangePasswordRequest;

public interface UserSettingsService {
    void changePassword(ChangePasswordRequest request);
}
