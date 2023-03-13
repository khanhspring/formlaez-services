package com.formlaez.service.internal.user.impl;

import com.formlaez.application.model.request.CreateUserRequest;
import com.formlaez.infrastructure.model.entity.JpaUser;
import com.formlaez.infrastructure.repository.JpaUserRepository;
import com.formlaez.service.internal.user.UserInternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserInternalServiceImpl implements UserInternalService {

    private final JpaUserRepository jpaUserRepository;

    @Override
    @Transactional
    public UUID create(CreateUserRequest request) {
        var user = JpaUser.builder()
                .id(request.getId())
                .email(request.getEmail())
                .username(request.getUsername())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .status(request.getStatus())
                .build();
        return jpaUserRepository.save(user)
                .getId();
    }
}
