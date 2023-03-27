package com.formlaez.service.email.impl;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.SendTemplatedEmailRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.formlaez.application.model.request.EmailTemplatedSendingRequest;
import com.formlaez.infrastructure.configuration.exception.ApplicationException;
import com.formlaez.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class SesEmailServiceImpl implements EmailService {

    private final AmazonSimpleEmailService  emailService;
    private final ObjectMapper objectMapper;

    @Override
    public void sendTemplatedEmail(EmailTemplatedSendingRequest request) {
        var destination = new Destination()
                .withToAddresses(request.getToAddresses());

        String templateData = null;
        if (Objects.nonNull(request.getData())) {
            try {
                templateData = objectMapper.writeValueAsString(request.getData());
            } catch (JsonProcessingException e) {
                log.error("Error when process email template data", e);
                throw new ApplicationException("Error when process email template data");
            }
        }

        var templatedEmailRequest = new SendTemplatedEmailRequest()
                .withDestination(destination)
                .withTemplate(request.getTemplateId())
                .withTemplateData(templateData)
                .withSource(request.getFromAddress());

        var result = emailService.sendTemplatedEmail(templatedEmailRequest);
        log.info("Sent email successfully, message id [{}]", result.getMessageId());
    }
}
