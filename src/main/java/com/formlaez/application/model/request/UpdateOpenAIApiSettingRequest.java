package com.formlaez.application.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOpenAIApiSettingRequest {
    private Long workspaceId;
    private String apiKey;
    private String model;
}
