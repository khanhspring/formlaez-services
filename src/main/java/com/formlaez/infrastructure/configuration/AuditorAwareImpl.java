package com.formlaez.infrastructure.configuration;

import com.formlaez.infrastructure.util.AuthUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return AuthUtils.currentUserId();
    }
}
