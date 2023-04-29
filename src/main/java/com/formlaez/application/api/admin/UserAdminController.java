package com.formlaez.application.api.admin;

import com.formlaez.application.model.request.SearchUserRequest;
import com.formlaez.application.model.response.UserResponse;
import com.formlaez.service.admin.user.UserAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
