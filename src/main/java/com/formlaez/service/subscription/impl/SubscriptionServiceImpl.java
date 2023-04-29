package com.formlaez.service.subscription.impl;

import com.formlaez.application.model.request.CancelSubscriptionRequest;
import com.formlaez.application.model.request.ChangeWorkspacePlanRequest;
import com.formlaez.application.model.request.CreateSubscriptionRequest;
import com.formlaez.application.model.response.SubscriptionResponse;
import com.formlaez.infrastructure.client.PaddleClient;
import com.formlaez.infrastructure.configuration.exception.ApplicationException;
import com.formlaez.infrastructure.configuration.exception.InvalidParamsException;
import com.formlaez.infrastructure.configuration.exception.ResourceNotFoundException;
import com.formlaez.infrastructure.enumeration.SubscriptionStatus;
import com.formlaez.infrastructure.enumeration.WorkspaceType;
import com.formlaez.infrastructure.model.entity.JpaSubscription;
import com.formlaez.infrastructure.property.PaddleProperties;
import com.formlaez.infrastructure.repository.JpaSubscriptionRepository;
import com.formlaez.infrastructure.repository.JpaUserRepository;
import com.formlaez.infrastructure.repository.JpaWorkspaceRepository;
import com.formlaez.service.subscription.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final JpaSubscriptionRepository jpaSubscriptionRepository;
    private final JpaWorkspaceRepository jpaWorkspaceRepository;
    private final JpaUserRepository jpaUserRepository;
    private final PaddleClient paddleClient;
    private final PaddleProperties properties;

    @Override
    @Transactional
    public void create(CreateSubscriptionRequest request) {
        var workspace = jpaWorkspaceRepository.findById(request.getWorkspaceId())
                .orElseThrow();
        var subscribedUser = jpaUserRepository.findById(request.getSubscribedUserId())
                .orElseThrow();

        var currentSubscriptionOpt = jpaSubscriptionRepository.getCurrentByWorkspaceId(request.getWorkspaceId(), Instant.now());
        if (currentSubscriptionOpt.isPresent()) {
            var currentSubscription = currentSubscriptionOpt.get();
            currentSubscription.setValidTill(Instant.now());
            jpaSubscriptionRepository.save(currentSubscription);
        }

        var subscription = JpaSubscription.builder()
                .workspace(workspace)
                .workspaceType(request.getWorkspaceType())
                .status(SubscriptionStatus.Active)
                .validFrom(request.getValidFrom())
                .validTill(request.getValidTill())
                .lastRenewedDate(Instant.now())
                .cancelUrl(request.getCancelUrl())
                .subscribedBy(subscribedUser)
                .externalId(request.getExternalId())
                .build();
        jpaSubscriptionRepository.save(subscription);

        workspace.setType(request.getWorkspaceType());
        jpaWorkspaceRepository.save(workspace);
    }

    @Override
    @Transactional
    public void cancel(CancelSubscriptionRequest request) {
        var subscription = jpaSubscriptionRepository.findByExternalId(request.getExternalId())
                .orElseThrow(ResourceNotFoundException::new);
        subscription.setStatus(SubscriptionStatus.Cancelled);
        subscription.setValidTill(request.getCancellationEffectiveDate());
        jpaSubscriptionRepository.save(subscription);
    }

    @Override
    @Transactional(readOnly = true)
    public SubscriptionResponse getCurrent(Long workspaceId) {
        var subscription = jpaSubscriptionRepository.getCurrentByWorkspaceIdIncludeExpired(workspaceId, Instant.now())
                .orElse(null);

        if (Objects.isNull(subscription)) {
            return SubscriptionResponse.builder()
                    .build();
        }

        return SubscriptionResponse.builder()
                .cancelUrl(subscription.getCancelUrl())
                .validFrom(subscription.getValidFrom())
                .validTill(subscription.getValidTill())
                .workspaceType(subscription.getWorkspaceType())
                .status(subscription.getStatus())
                .build();
    }

    @Override
    @Transactional
    public void changePlan(ChangeWorkspacePlanRequest request) {
        var workspace = jpaWorkspaceRepository.findById(request.getWorkspaceId())
                .orElseThrow();
        if (workspace.getType() == WorkspaceType.Free) {
            throw new InvalidParamsException();
        }

        if (workspace.getType() == request.getType()) {
            throw new InvalidParamsException();
        }

        var subscription = jpaSubscriptionRepository.getCurrentByWorkspaceId(request.getWorkspaceId(), Instant.now())
                .orElseThrow();

        var planId = properties.getPlanId(request.getType());
        var response = paddleClient.updateUser(subscription.getExternalId(), planId);
        if (!response.isSuccess()) {
            throw new ApplicationException("Can not change plan for now!");
        }
        if (Objects.nonNull(response.getResponse())
                && Objects.nonNull(response.getResponse().getNextPayment())
                && Objects.nonNull(response.getResponse().getNextPayment().getDate())) {
            var nextPaymentDateStr = response.getResponse().getNextPayment().getDate();
            var nextPaymentDate = LocalDate.parse(nextPaymentDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    .atStartOfDay(ZoneId.systemDefault())
                    .toInstant();
            subscription.setValidTill(nextPaymentDate);
        }
        subscription.setLastRenewedDate(Instant.now());
        subscription.setWorkspaceType(request.getType());
        jpaSubscriptionRepository.save(subscription);

        workspace.setType(request.getType());
        jpaWorkspaceRepository.save(workspace);
    }
}
