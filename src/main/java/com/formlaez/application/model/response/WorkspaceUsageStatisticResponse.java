package com.formlaez.application.model.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceUsageStatisticResponse {
    private int totalForm;
    private int totalMember;
    private long totalFileStorage;
    private int totalSubmission;
    private int totalDocumentMerge;
}
