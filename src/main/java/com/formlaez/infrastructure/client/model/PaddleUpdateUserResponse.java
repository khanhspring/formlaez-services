package com.formlaez.infrastructure.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaddleUpdateUserResponse {
    @JsonProperty("subscription_id")
    private String subscriptionId;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("plan_id")
    private String planId;
    @JsonProperty("next_payment")
    private NextPayment nextPayment;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NextPayment {

        @JsonProperty("amount")
        private String amount;
        @JsonProperty("currency")
        private String currency;
        @JsonProperty("date")
        private String date;
    }
}
