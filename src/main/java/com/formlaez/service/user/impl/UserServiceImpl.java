package com.formlaez.service.user.impl;

import com.formlaez.application.model.request.CreateUserRequest;
import com.formlaez.application.model.response.MemberWorkspaceResponse;
import com.formlaez.application.model.response.UserSessionResponse;
import com.formlaez.infrastructure.converter.WorkspaceResponseConvertor;
import com.formlaez.infrastructure.model.entity.JpaUser;
import com.formlaez.infrastructure.model.entity.JpaWorkspaceMember;
import com.formlaez.infrastructure.repository.JpaUserRepository;
import com.formlaez.infrastructure.repository.JpaWorkspaceMemberRepository;
import com.formlaez.infrastructure.util.AuthUtils;
import com.formlaez.service.user.UserService;
import com.formlaez.service.user.UserSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.formlaez.infrastructure.enumeration.WorkspaceMemberRole.Owner;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

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
