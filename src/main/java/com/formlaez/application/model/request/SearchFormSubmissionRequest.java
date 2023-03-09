package com.formlaez.application.model.request;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class SearchFormSubmissionRequest {

    private String formCode;

    private Instant fromDate;
    private Instant toDate;
}
