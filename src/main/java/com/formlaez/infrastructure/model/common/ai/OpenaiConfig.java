package com.formlaez.infrastructure.model.common.ai;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpenaiConfig {
    private String apiKey;
    private String model;
}
