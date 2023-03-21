package com.formlaez.infrastructure.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaddleResponse<T> {
    @JsonProperty("success")
    private boolean success;

    private T response;
}
