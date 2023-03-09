package com.formlaez.application.api;

import com.formlaez.application.model.response.TokenResponse;
import com.formlaez.infrastructure.client.AuthClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class TokenController {

    private final AuthClient authClient;

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
}
