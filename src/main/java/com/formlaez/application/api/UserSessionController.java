package com.formlaez.application.api;

import com.formlaez.application.model.response.UserSessionResponse;
import com.formlaez.service.user.UserSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/user/session")
public class UserSessionController {

    private final UserSessionService userSessionService;

    @GetMapping
    public UserSessionResponse getCurrentUserSession() {
        return userSessionService.getCurrentUserSession();
    }
}
