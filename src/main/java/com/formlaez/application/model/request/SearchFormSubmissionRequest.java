package com.formlaez.application.model.request;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchFormSubmissionRequest {

    private String formCode;

    private Instant fromDate;
    private Instant toDate;
}
