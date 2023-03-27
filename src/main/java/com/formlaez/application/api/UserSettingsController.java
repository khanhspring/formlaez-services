package com.formlaez.application.api;

import com.formlaez.application.model.request.ChangePasswordRequest;
import com.formlaez.application.model.response.UserSessionResponse;
import com.formlaez.service.user.UserSessionService;
import com.formlaez.service.user.UserSettingsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/user/settings")
public class UserSettingsController {

    private final UserSettingsService userSettingsService;

    @PostMapping("change-password")
    public void changePassword(@RequestBody @Valid ChangePasswordRequest request) {
        userSettingsService.changePassword(request);
    }
}
