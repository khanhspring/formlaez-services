package com.formlaez.infrastructure.util;

import com.formlaez.infrastructure.configuration.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthUtils {

    private static final String USER_ID_CLAIM = "uid";

    public static Optional<UUID> currentUserId() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(auth)) {
            return Optional.empty();
        }
        var principal = auth.getPrincipal();
        if (!(principal instanceof Jwt jwt)) {
            return Optional.empty();
        }
        String uid = jwt.getClaim(USER_ID_CLAIM);
        return Optional.of(UUID.fromString(uid));
    }

    public static boolean isAuthenticated() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(auth)) {
            return false;
        }
        if (auth instanceof AnonymousAuthenticationToken) {
            return false;
        }
        if ("anonymousUser".equals(auth.getPrincipal())) {
            return false;
        }
        return auth.isAuthenticated();
    }

    public static UUID currentUserIdOrElseThrow() {
        return currentUserId().orElseThrow(UnauthorizedException::new);
    }
}
