package com.formlaez.application.model.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceOpenAIApiResponse {
    private String model;
    private String apiKey;
}