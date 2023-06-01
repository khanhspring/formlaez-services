package com.formlaez.service.email.impl;

import com.formlaez.application.model.request.EmailTemplatedSendingRequest;
import com.formlaez.infrastructure.configuration.exception.ApplicationException;
import com.formlaez.service.email.EmailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final SendGrid sendGrid;

    @Override
    public void sendTemplatedEmail(EmailTemplatedSendingRequest request) {
        Email from = new Email(request.getFromAddress());

        Mail mail = new Mail();
        mail.setFrom(from);
        mail.setTemplateId(request.getTemplateId());

        if (Objects.nonNull(request.getData())) {
            for (var entryData : request.getData().entrySet()) {
                mail.addCustomArg(entryData.getKey(), entryData.getValue());
            }
        }

        for (var toAddress : request.getToAddresses()) {
            Email to = new Email(toAddress);
            Personalization personalization = new Personalization();
            personalization.addTo(to);
            mail.addPersonalization(personalization);
        }

        try {
            Request sendRequest = new Request();
            sendRequest.setMethod(Method.POST);
            sendRequest.setEndpoint("mail/send");
            sendRequest.setBody(mail.build());
            Response response = sendGrid.api(sendRequest);
            log.info("Email sent, status {}", response.getStatusCode());
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }
}
