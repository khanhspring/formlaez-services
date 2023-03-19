package com.formlaez.application.model.response.form;

import com.formlaez.application.model.response.TeamResponse;
import com.formlaez.application.model.response.WorkspaceResponse;
import com.formlaez.infrastructure.enumeration.FormCoverType;
import com.formlaez.infrastructure.enumeration.FormScope;
import com.formlaez.infrastructure.enumeration.FormSharingScope;
import com.formlaez.infrastructure.enumeration.FormStatus;
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
    private FormStatus status;
    private FormSharingScope sharingScope;
    private boolean acceptResponses;
    private boolean allowPrinting;
    private boolean allowResponseEditing;
    private List<FormPageResponse> pages;

    private FormEndingResponse ending;
    private TeamResponse team;
    private WorkspaceResponse workspace;

    private Instant createdDate;
    private Instant lastModifiedDate;
}
