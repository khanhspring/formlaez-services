package com.formlaez.service.user.impl;

import com.formlaez.application.model.request.ChangePasswordRequest;
import com.formlaez.infrastructure.client.UserAdminClient;
import com.formlaez.infrastructure.client.model.ChangeUserPasswordRequest;
import com.formlaez.infrastructure.configuration.exception.InvalidParamsException;
import com.formlaez.infrastructure.repository.JpaUserRepository;
import com.formlaez.infrastructure.util.AuthUtils;
import com.formlaez.service.user.UserSettingsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
public class UserSettingsServiceImpl implements UserSettingsService {

    private final UserAdminClient userAdminClient;
    private final JpaUserRepository jpaUserRepository;
    private final PasswordEncoder passwordEncoder;

    public UserSettingsServiceImpl(@Qualifier("userAdminFirebaseClient") UserAdminClient userAdminClient,
                                   JpaUserRepository jpaUserRepository,
                                   PasswordEncoder passwordEncoder) {
        this.userAdminClient = userAdminClient;
        this.jpaUserRepository = jpaUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest request) {
        var currentUserId = AuthUtils.currentUserIdOrElseThrow();
        var user = jpaUserRepository.findById(currentUserId)
                .orElseThrow();

        if (ObjectUtils.isEmpty(user.getPassword())) {
            if (!ObjectUtils.isEmpty(request.getCurrentPassword())) {
                throw new InvalidParamsException("Current password is incorrect");
            }
        } else {
            if (ObjectUtils.isEmpty(request.getCurrentPassword())) {
                throw new InvalidParamsException("Current password is incorrect");
            }
            if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
                throw new InvalidParamsException("Current password is incorrect");
            }
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        jpaUserRepository.save(user);

        var changeUserPasswordRequest = ChangeUserPasswordRequest.builder()
                .currentPassword(request.getCurrentPassword())
                .newPassword(request.getNewPassword())
                .userId(currentUserId)
                .build();
        userAdminClient.changeUserPassword(changeUserPasswordRequest);
    }
}
