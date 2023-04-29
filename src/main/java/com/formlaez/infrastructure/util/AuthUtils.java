package com.formlaez.infrastructure.util;

import com.formlaez.infrastructure.configuration.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthUtils {

    public static final String USER_ID_CLAIM = "user_id";

    public static Optional<String> currentUserId() {
        var jwt = AuthUtils.getPrinciple();
        if (jwt.isEmpty()) {
            return Optional.empty();
        }
        String uid = jwt.get().getClaim(USER_ID_CLAIM);
        return Optional.of(uid);
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

    public static String currentUserIdOrElseThrow() {
        return currentUserId().orElseThrow(UnauthorizedException::new);
    }

    public static Optional<Jwt> getPrinciple() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(auth)) {
            return Optional.empty();
        }
        var principal = auth.getPrincipal();
        if (!(principal instanceof Jwt jwt)) {
            return Optional.empty();
        }
        return Optional.of(jwt);
    }
}
