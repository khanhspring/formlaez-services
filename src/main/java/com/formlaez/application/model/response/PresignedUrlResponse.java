package com.formlaez.application.model.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PresignedUrlResponse {
    private String url;
}
