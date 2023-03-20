package com.formlaez.application.api.webhook;

import com.formlaez.service.paddle.PaddleWebhookHandlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/webhook/paddle")
public class PaddleWebhookController {

    private final PaddleWebhookHandlerService paddleWebhookHandlerService;

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void handle(HttpEntity<String> httpEntity) {
        paddleWebhookHandlerService.handle(httpEntity.getBody());
    }
}
