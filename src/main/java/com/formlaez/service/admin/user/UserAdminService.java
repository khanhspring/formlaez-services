package com.formlaez.service.admin.user;

import com.formlaez.application.model.request.*;
import com.formlaez.application.model.response.UserResponse;
import com.formlaez.application.model.response.WorkspaceMemberResponse;
import com.formlaez.application.model.response.WorkspaceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserAdminService {
    Page<UserResponse> search(SearchUserRequest request, Pageable pageable);
}
