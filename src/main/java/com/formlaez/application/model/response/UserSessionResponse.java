package com.formlaez.application.model.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSessionResponse {

    private boolean onboarded;
    private MemberWorkspaceResponse lastAccessedWorkspace;
    private List<MemberWorkspaceResponse> joinedWorkspaces;
}
