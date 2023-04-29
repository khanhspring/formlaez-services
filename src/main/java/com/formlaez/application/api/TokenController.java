package com.formlaez.application.api;

import com.formlaez.application.model.request.ValidateTokenRequest;
import com.formlaez.application.model.response.TokenResponse;
import com.formlaez.infrastructure.client.AuthClient;
import com.formlaez.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class TokenController {

    private final AuthClient authClient;
    private final AuthService authService;

    @GetMapping("token")
    public TokenResponse token(@RequestParam String code) {
        var token = authClient.getTokenByCode(code);
        return TokenResponse.builder()
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .tokenType(token.getTokenType())
                .expiresIn(token.getExpiresIn())
                .build();
    }

    @PostMapping("token/validation")
    public void validate(@Valid @RequestBody ValidateTokenRequest request) {
        authService.validateToken(request.getToken());
    }
}
