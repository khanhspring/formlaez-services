package com.formlaez.service.admin.user.impl;

import com.formlaez.application.model.request.SearchUserRequest;
import com.formlaez.application.model.response.UserResponse;
import com.formlaez.infrastructure.converter.UserResponseConverter;
import com.formlaez.infrastructure.repository.JpaUserRepository;
import com.formlaez.service.admin.user.UserAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAdminServiceImpl implements UserAdminService {

    private final JpaUserRepository jpaUserRepository;
    private final UserResponseConverter userResponseConverter;

    @Override
    public Page<UserResponse> search(SearchUserRequest request, Pageable pageable) {
        return jpaUserRepository.search(request, pageable)
                .map(userResponseConverter::convert);
    }
}
