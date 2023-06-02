package com.formlaez.service.auth.impl;

import com.formlaez.infrastructure.configuration.exception.ApplicationException;
import com.formlaez.infrastructure.configuration.exception.InvalidParamsException;
import com.formlaez.infrastructure.enumeration.UserStatus;
import com.formlaez.infrastructure.model.entity.JpaUser;
import com.formlaez.infrastructure.repository.JpaUserRepository;
import com.formlaez.service.auth.AuthService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JpaUserRepository jpaUserRepository;

    @Override
    @Transactional
    public void validateToken(String token) {
        FirebaseToken decodedToken;
        try {
            decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
        } catch (FirebaseAuthException e) {
            throw new ApplicationException("Token is invalid");
        }
        var userId = decodedToken.getUid();

        var existing = jpaUserRepository.findById(userId);
        if (existing.isPresent()) {
            // do nothing
            return;
        }

        var emailVerified = decodedToken.isEmailVerified();
        if (!Boolean.TRUE.equals(emailVerified)) {
            throw new InvalidParamsException("Email is not verified");
        }

        var name = decodedToken.getName();
        var firstAndLastName = extractName(name);

        var email = decodedToken.getEmail();

        var newUser = JpaUser.builder()
                .id(userId)
                .email(email)
                .username(email)
                .firstName(firstAndLastName.getT1())
                .lastName(firstAndLastName.getT2())
                .status(UserStatus.Active)
                .build();
        jpaUserRepository.save(newUser);
    }

    private Tuple2<String, String> extractName(String name) {
        if (!name.contains(" ")) {
            return Tuples.of(name, name);
        }

        String[] arr = name.split(" ");
        String lastName = arr[arr.length - 1];

        arr[arr.length - 1] = "";
        String firstName = String.join(" ", arr);
        return Tuples.of(firstName.trim(), lastName.trim());
    }
}
