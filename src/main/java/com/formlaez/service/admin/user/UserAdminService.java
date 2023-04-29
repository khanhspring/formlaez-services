package com.formlaez.service.admin.user;

import com.formlaez.application.model.request.SearchUserRequest;
import com.formlaez.application.model.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserAdminService {
    Page<UserResponse> search(SearchUserRequest request, Pageable pageable);
}
