package com.formlaez.application.model.response;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormDocumentTemplateResponse {
    private Long id;
    private String code;
    private String attachmentCode;
    private String title;
    private String description;
    private String originalName;
    private String extension;
    private long size;
    private Instant createdDate;
    private Instant lastModifiedDate;
}
