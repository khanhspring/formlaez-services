package com.formlaez.infrastructure.model.common.paddle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaddlePassThrough {

    private Long workspaceId;
    private String userId;
}
