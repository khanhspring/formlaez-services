package com.formlaez.infrastructure.client.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.formlaez.infrastructure.client.PaddleClient;
import com.formlaez.infrastructure.client.model.AuthToken;
import com.formlaez.infrastructure.client.model.PaddleResponse;
import com.formlaez.infrastructure.client.model.PaddleUpdateUserResponse;
import com.formlaez.infrastructure.configuration.exception.ApplicationException;
import com.formlaez.infrastructure.property.PaddleProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
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
public class PaddleClientImpl implements PaddleClient {

    private final PaddleProperties properties;
    private final WebClient webClient;

    public PaddleClientImpl(PaddleProperties properties) {
        this.properties = properties;

        webClient = WebClient.builder()
                .baseUrl(properties.getApiBaseUrl())
                .build();
    }

    @Override
    public PaddleResponse<PaddleUpdateUserResponse> updateUser(String subscriptionId, String planId) {
        try {
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("vendor_id", properties.getVendorId() + "");
            formData.add("vendor_auth_code", properties.getVendorAuthCode());
            formData.add("subscription_id", subscriptionId);
            formData.add("plan_id", planId);
            formData.add("bill_immediately", "true");
            formData.add("prorate", "true");

            var responseType = new ParameterizedTypeReference<PaddleResponse<PaddleUpdateUserResponse>>(){};
            ResponseEntity<PaddleResponse<PaddleUpdateUserResponse>> response = webClient.post()
                    .uri("/subscription/users/update")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .toEntity(responseType)
                    .block();
            return Objects.requireNonNull(response).getBody();
        } catch (WebClientResponseException e) {
            log.error("Update Paddle subscription error, response [{}]", e.getResponseBodyAsString(), e);
            throw new ApplicationException(e);
        } catch (Exception e) {
            log.error("Update Paddle subscription", e);
            throw new ApplicationException(e);
        }
    }
}
