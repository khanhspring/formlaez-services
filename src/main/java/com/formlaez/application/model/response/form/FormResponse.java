package com.formlaez.application.model.response.form;

import com.formlaez.infrastructure.enumeration.FormCoverType;
import com.formlaez.infrastructure.enumeration.FormScope;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormResponse {
    private Long id;
    private String code;
    private String title;
    private String description;
    private FormScope scope;
    private FormCoverType coverType;
    private String coverColor;
    private String coverImageUrl;

    private List<FormPageResponse> pages;

    private Instant createdDate;
    private Instant lastModifiedDate;
}
