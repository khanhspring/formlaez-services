package com.formlaez.application.api.internal;

import com.formlaez.application.model.request.CreateUserRequest;
import com.formlaez.application.model.response.ResponseId;
import com.formlaez.service.internal.user.UserInternalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/internal/users")
public class UserInternalController {

    private final UserInternalService userInternalService;

    @PostMapping
    public ResponseId<UUID> create(@RequestBody @Valid CreateUserRequest request) {
        return ResponseId.of(userInternalService.create(request));
    }
}
