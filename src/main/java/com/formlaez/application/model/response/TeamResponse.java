package com.formlaez.application.model.response;

import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamResponse {
    private Long id;
    private String code;
    private String name;
    private String description;
    private Instant createdDate;
    private Instant lastModifiedDate;

    private List<TeamMemberResponse> members;
}
