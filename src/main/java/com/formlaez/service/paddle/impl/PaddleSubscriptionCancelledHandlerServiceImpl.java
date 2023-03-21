package com.formlaez.service.paddle.impl;

import com.formlaez.application.model.request.CancelSubscriptionRequest;
import com.formlaez.infrastructure.configuration.exception.InvalidParamsException;
import com.formlaez.infrastructure.enumeration.PaddleSubscriptionStatus;
import com.formlaez.service.paddle.PaddleSubscriptionCancelledHandlerService;
import com.formlaez.service.subscription.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class PaddleSubscriptionCancelledHandlerServiceImpl implements PaddleSubscriptionCancelledHandlerService {
    private final SubscriptionService subscriptionService;

    @Override
    @Transactional
    public void handle(TreeMap<String, String> dataMap) {
        var subscriptionId = dataMap.get("subscription_id");
        var cancellationEffectiveDate = convertCancellationEffectiveDate(dataMap.get("cancellation_effective_date"));

        var cancelRequest = CancelSubscriptionRequest.builder()
                .cancellationEffectiveDate(cancellationEffectiveDate)
                .externalId(subscriptionId)
                .build();
        subscriptionService.cancel(cancelRequest);
    }

    private Instant convertCancellationEffectiveDate(String cancellationEffectiveDate) {
        if (ObjectUtils.isEmpty(cancellationEffectiveDate)) {
            return null;
        }
        var dateFormat = LocalDate.parse(cancellationEffectiveDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return dateFormat.atStartOfDay(ZoneId.systemDefault()).toInstant();
    }
}
