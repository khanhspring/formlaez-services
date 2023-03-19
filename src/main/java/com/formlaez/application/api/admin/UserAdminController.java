package com.formlaez.application.api.admin;

import com.formlaez.application.model.request.*;
import com.formlaez.application.model.response.ResponseId;
import com.formlaez.application.model.response.TeamMemberResponse;
import com.formlaez.application.model.response.TeamResponse;
import com.formlaez.application.model.response.UserResponse;
import com.formlaez.service.admin.team.TeamAdminService;
import com.formlaez.service.admin.team.TeamMemberAdminService;
import com.formlaez.service.admin.user.UserAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/admin/users")
public class UserAdminController {

    private final UserAdminService userAdminService;

    @GetMapping
    public Page<UserResponse> search(@Valid SearchUserRequest request,
                                     @PageableDefault(size = 20, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return userAdminService.search(request, pageable);
    }
}
