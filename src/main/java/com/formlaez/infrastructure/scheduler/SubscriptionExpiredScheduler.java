package com.formlaez.infrastructure.scheduler;

import com.formlaez.infrastructure.enumeration.WorkspaceType;
import com.formlaez.infrastructure.repository.JpaSubscriptionRepository;
import com.formlaez.infrastructure.repository.JpaWorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class SubscriptionExpiredScheduler {

    public static final int EXPIRED_DAYS = 2; // 2 days before
    private final JpaWorkspaceRepository jpaWorkspaceRepository;

    @Scheduled(cron = "${formlaez.scheduler.subscription-expired.cron}")
    public void scanSubscriptionExpired() {
        var expiredDate = Instant.now().minus(EXPIRED_DAYS, ChronoUnit.DAYS);
        var hasNext = false;
        do {
            Pageable pageable = PageRequest.of(0, 20);
            var results = jpaWorkspaceRepository.getWorkspaceSubscriptionExpired(expiredDate, pageable);
            for (var expiredWorkspace : results.getContent()) {
                expiredWorkspace.setType(WorkspaceType.Free);
                jpaWorkspaceRepository.save(expiredWorkspace);
            }
            hasNext = results.hasNext();
        } while (hasNext);
    }
}
