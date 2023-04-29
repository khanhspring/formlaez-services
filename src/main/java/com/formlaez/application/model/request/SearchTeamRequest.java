package com.formlaez.application.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchTeamRequest {

    @NotNull
    private Long workspaceId;

    private String keyword;

    private String memberId;
}
