package com.formlaez.service.paddle.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.formlaez.application.model.request.CreateSubscriptionRequest;
import com.formlaez.infrastructure.configuration.exception.InvalidParamsException;
import com.formlaez.infrastructure.enumeration.PaddleSubscriptionStatus;
import com.formlaez.infrastructure.model.common.paddle.PaddlePassThrough;
import com.formlaez.infrastructure.property.PaddleProperties;
import com.formlaez.service.paddle.PaddleSubscriptionCreatedHandlerService;
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

@Service
@RequiredArgsConstructor
public class PaddleSubscriptionCreatedHandlerServiceImpl implements PaddleSubscriptionCreatedHandlerService {

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

        var cancelUrl = decodeUrl(dataMap.get("cancel_url"));
        var subscriptionId = dataMap.get("subscription_id");

        var passThrough = convertPassThrough(dataMap.get("passthrough"));
        var nextBillDate = convertNextBillDate(dataMap.get("next_bill_date"));

        var subscriptionPlanId = dataMap.get("subscription_plan_id");
        var workspaceType = properties.getWorkspaceType(subscriptionPlanId);

        var createSubscriptionRequest = CreateSubscriptionRequest.builder()
                .validFrom(Instant.now())
                .validTill(nextBillDate)
                .subscribedUserId(passThrough.getUserId())
                .workspaceId(passThrough.getWorkspaceId())
                .externalId(subscriptionId)
                .cancelUrl(cancelUrl)
                .workspaceType(workspaceType)
                .build();
        subscriptionService.create(createSubscriptionRequest);
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

    private Instant convertNextBillDate(String nextBillDate) {
        if (ObjectUtils.isEmpty(nextBillDate)) {
            return null;
        }
        var dateFormat = LocalDate.parse(nextBillDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return dateFormat.atStartOfDay(ZoneId.systemDefault()).toInstant();
    }

    private String decodeUrl(String encodedUrl) {
        if (ObjectUtils.isEmpty(encodedUrl)) {
            return encodedUrl;
        }
        return URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8);
    }
}
