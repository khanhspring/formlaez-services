package com.formlaez.application.model.response.form;

import com.formlaez.infrastructure.enumeration.FormCoverType;
import com.formlaez.infrastructure.enumeration.FormScope;
import com.formlaez.infrastructure.enumeration.FormSharingScope;
import com.formlaez.infrastructure.enumeration.FormStatus;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasicFormResponse {
    private Long id;
    private String code;
    private String title;
    private String description;
    private FormScope scope;
    private FormCoverType coverType;
    private String coverColor;
    private String coverImageUrl;
    private FormStatus status;
    private FormSharingScope sharingScope;
    private boolean acceptResponses;
    private boolean allowPrinting;
    private boolean allowResponseEditing;
    private Instant createdDate;
    private Instant lastModifiedDate;
}
