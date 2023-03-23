package com.formlaez.service.signuprequest;

import com.formlaez.application.model.request.ConfirmSignUpRequest;
import com.formlaez.application.model.request.SignUpRequest;

public interface SignUpRequestService {
    void signUp(SignUpRequest request);
    void confirm(ConfirmSignUpRequest request);
}
