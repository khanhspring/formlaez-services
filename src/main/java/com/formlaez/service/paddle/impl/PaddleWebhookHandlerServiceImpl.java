package com.formlaez.service.paddle.impl;

import com.formlaez.infrastructure.configuration.exception.InvalidParamsException;
import com.formlaez.infrastructure.enumeration.PaddleAlertName;
import com.formlaez.infrastructure.property.PaddleProperties;
import com.formlaez.service.paddle.PaddleSubscriptionCancelledHandlerService;
import com.formlaez.service.paddle.PaddleSubscriptionCreatedHandlerService;
import com.formlaez.service.paddle.PaddleWebhookHandlerService;
import com.jamiussiam.paddle.verifier.Verifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.TreeMap;

@Slf4j
@Service
public class PaddleWebhookHandlerServiceImpl implements PaddleWebhookHandlerService {

    private final Verifier verifier;
    private final PaddleSubscriptionCreatedHandlerService subscriptionCreatedHandler;
    private final PaddleSubscriptionCancelledHandlerService subscriptionCanceledHandler;

    public PaddleWebhookHandlerServiceImpl(PaddleProperties properties,
                                           PaddleSubscriptionCreatedHandlerService subscriptionCreatedHandler,
                                           PaddleSubscriptionCancelledHandlerService subscriptionCanceledHandler) throws IOException {
        var publicKey = properties.getPublicKey().getContentAsString(StandardCharsets.UTF_8)
                .replace("\r", ""); // the lib just support only \n as line break
        verifier = new Verifier(publicKey);

        this.subscriptionCreatedHandler = subscriptionCreatedHandler;
        this.subscriptionCanceledHandler = subscriptionCanceledHandler;
    }

    @Async
    @Override
    public void handle(String requestBody) {
        log.info("[Paddle event] [{}]", requestBody);
        var isValid = verifier.verifyDataWithSignature(requestBody);
        if (!isValid) {
            log.error("Paddle webhook is invalid, [{}]", requestBody);
            throw new InvalidParamsException("Paddle webhook is invalid");
        }
        var dataMap = getSortedMapFromBody(requestBody);
        var alertName = PaddleAlertName.of(dataMap.get("alert_name"));

        if (alertName == PaddleAlertName.subscription_created) {
            subscriptionCreatedHandler.handle(dataMap);
            return;
        }

        if (alertName == PaddleAlertName.subscription_cancelled) {
            subscriptionCanceledHandler.handle(dataMap);
            return;
        }
    }

    private TreeMap<String, String> getSortedMapFromBody(String data) {
        String[] split = data.split("&");
        TreeMap<String, String> ret = new TreeMap<>();

        for(String s : split) {
            String[] half = s.split("=", 2);
            ret.put(half[0], half[1]);
        }

        ret.remove("p_signature");
        return ret;
    }
}
