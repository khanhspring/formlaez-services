package com.formlaez.service.email;

import com.formlaez.application.model.request.EmailTemplatedSendingRequest;

public interface EmailService {
    void sendTemplatedEmail(EmailTemplatedSendingRequest request);
}
