package com.formlaez.application.api;

import com.formlaez.application.model.request.ValidateTokenRequest;
import com.formlaez.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class TokenController {

    private final AuthService authService;

    @PostMapping("token/validation")
    public void validate(@Valid @RequestBody ValidateTokenRequest request) {
        authService.validateToken(request.getToken());
    }
}
