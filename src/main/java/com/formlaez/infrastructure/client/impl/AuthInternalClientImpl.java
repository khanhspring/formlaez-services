package com.formlaez.infrastructure.client.impl;

import com.formlaez.application.model.response.ResponseId;
import com.formlaez.infrastructure.client.AuthInternalClient;
import com.formlaez.infrastructure.client.model.ChangeUserPasswordRequest;
import com.formlaez.infrastructure.client.model.CreateUserRequest;
import com.formlaez.infrastructure.configuration.exception.ApplicationException;
import com.formlaez.infrastructure.property.AuthInternalClientProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class AuthInternalClientImpl implements AuthInternalClient {

    private final WebClient webClient;

    public AuthInternalClientImpl(AuthInternalClientProperties properties) {
        webClient = WebClient.builder()
                .baseUrl(properties.getBaseUrl())
                .defaultHeader(HttpHeaders.AUTHORIZATION, properties.getAuthorization())
                .build();
    }

    @Override
    public ResponseId<UUID> createUser(CreateUserRequest request) {
        try {
            ResponseEntity<ResponseId<UUID>> response = webClient.post()
                    .uri("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .toEntity(new ParameterizedTypeReference<ResponseId<UUID>>() {})
                    .block();
            return Objects.requireNonNull(response).getBody();
        } catch (WebClientResponseException e) {
            log.error("Create user error, response [{}]", e.getResponseBodyAsString(), e);
            throw new ApplicationException(e);
        } catch (Exception e) {
            log.error("Create user error", e);
            throw new ApplicationException(e);
        }
    }

    @Override
    public void changeUserPassword(ChangeUserPasswordRequest request) {
        try {
            webClient.post()
                    .uri("/users/" + request.getUserId() + "/change-password")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Change user password error, response [{}]", e.getResponseBodyAsString(), e);
            throw new ApplicationException(e);
        } catch (Exception e) {
            log.error("Change user password error", e);
            throw new ApplicationException(e);
        }
    }
}