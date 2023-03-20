package com.formlaez.service.paddle.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.formlaez.application.model.request.CancelSubscriptionRequest;
import com.formlaez.application.model.request.CreateSubscriptionRequest;
import com.formlaez.infrastructure.configuration.exception.InvalidParamsException;
import com.formlaez.infrastructure.enumeration.PaddleSubscriptionStatus;
import com.formlaez.infrastructure.model.common.paddle.PaddlePassThrough;
import com.formlaez.infrastructure.property.PaddleProperties;
import com.formlaez.service.paddle.PaddleSubscriptionCancelledHandlerService;
import com.formlaez.service.subscription.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TreeMap;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaddleSubscriptionCancelledHandlerServiceImpl implements PaddleSubscriptionCancelledHandlerService {

    private final PaddleProperties properties;
    private final ObjectMapper objectMapper;
    private final SubscriptionService subscriptionService;

    @Override
    @Transactional
    public void handle(TreeMap<String, String> dataMap) {
        var status = PaddleSubscriptionStatus.valueOf(dataMap.get("status"));
        if (status != PaddleSubscriptionStatus.active) {
            throw new InvalidParamsException("Status is not active");
        }

        var subscriptionId = dataMap.get("subscription_id");
        var cancellationEffectiveDate = convertCancellationEffectiveDate(dataMap.get("cancellation_effective_date"));

        var cancelRequest = CancelSubscriptionRequest.builder()
                .cancellationEffectiveDate(cancellationEffectiveDate)
                .externalId(subscriptionId)
                .build();
        subscriptionService.cancel(cancelRequest);
    }

    private PaddlePassThrough convertPassThrough(String passThrough) {
        if (ObjectUtils.isEmpty(passThrough)) {
            throw new InvalidParamsException("PassThrough is empty");
        }
        try {
            var passThroughDecoded = decodeUrl(passThrough);
            return objectMapper.readValue(passThroughDecoded, PaddlePassThrough.class);
        } catch (JsonProcessingException e) {
            throw new InvalidParamsException("PassThrough is invalid");
        }
    }

    private Instant convertCancellationEffectiveDate(String cancellationEffectiveDate) {
        if (ObjectUtils.isEmpty(cancellationEffectiveDate)) {
            return null;
        }
        var dateFormat = LocalDate.parse(cancellationEffectiveDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return dateFormat.atStartOfDay(ZoneId.systemDefault()).toInstant();
    }

    private String decodeUrl(String encodedUrl) {
        if (ObjectUtils.isEmpty(encodedUrl)) {
            return encodedUrl;
        }
        return URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8);
    }
}
