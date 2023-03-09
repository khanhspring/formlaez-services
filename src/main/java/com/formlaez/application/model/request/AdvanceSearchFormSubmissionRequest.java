package com.formlaez.application.model.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
public class AdvanceSearchFormSubmissionRequest {

    private String formCode;

    private List<String> keywords;

    private Instant fromDate;
    private Instant toDate;

    public Map<String, String> keywordsToMap() {
        if (ObjectUtils.isEmpty(keywords)) {
            return null;
        }
        return keywords.stream()
                .filter(keyword -> keyword.matches("(.+)<<#>>(.+)"))
                .map(keyword -> keyword.split("<<#>>"))
                .collect(Collectors.toMap(arr -> arr[0], arr -> arr[1]));
    }
}
