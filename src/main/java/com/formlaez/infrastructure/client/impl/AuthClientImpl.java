package com.formlaez.infrastructure.client.impl;

import com.formlaez.infrastructure.client.AuthClient;
import com.formlaez.infrastructure.client.model.AuthToken;
import com.formlaez.infrastructure.configuration.exception.ApplicationException;
import com.formlaez.infrastructure.property.AuthClientProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Objects;

@Slf4j
@Service
public class AuthClientImpl implements AuthClient {

    private final WebClient webClient;
    private final AuthClientProperties properties;

    public AuthClientImpl(AuthClientProperties properties) {
        this.properties = properties;

        webClient = WebClient.builder()
                .baseUrl(properties.getBaseUrl())
                .defaultHeader(HttpHeaders.AUTHORIZATION, properties.getAuthorization())
                .build();
    }

    @Override
    public AuthToken getTokenByCode(String code) {
        try {
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("grant_type", "authorization_code");
            formData.add("redirect_uri", properties.getRedirectUri());
            formData.add("code", code);
            ResponseEntity<AuthToken> response = webClient.post()
                    .uri("/oauth2/token")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .toEntity(AuthToken.class)
                    .block();
            return Objects.requireNonNull(response).getBody();
        } catch (WebClientResponseException e) {
            log.error("Get token by code error, response [{}]", e.getResponseBodyAsString(), e);
            throw new ApplicationException(e);
        } catch (Exception e) {
            log.error("Get token by code error", e);
            throw new ApplicationException(e);
        }
    }
}
