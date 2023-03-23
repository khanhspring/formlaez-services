package com.formlaez.application.api;

import com.formlaez.application.model.request.ConfirmSignUpRequest;
import com.formlaez.application.model.request.SignUpRequest;
import com.formlaez.service.signuprequest.SignUpRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/auth/sign-up")
public class SignUpRequestController {

    private final SignUpRequestService signUpRequestService;

    @PostMapping
    public void signUp(@RequestBody @Valid SignUpRequest request) {
        signUpRequestService.signUp(request);
    }

    @PostMapping("confirmations")
    public void confirm(@RequestBody @Valid ConfirmSignUpRequest request) {
        signUpRequestService.confirm(request);
    }
}
