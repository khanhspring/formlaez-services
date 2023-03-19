package com.formlaez.service.admin.team;

import com.formlaez.application.model.request.AddTeamMemberRequest;
import com.formlaez.application.model.request.RemoveTeamMemberRequest;
import com.formlaez.application.model.request.SearchTeamMemberRequest;
import com.formlaez.application.model.request.UpdateTeamMemberRoleRequest;
import com.formlaez.application.model.response.TeamMemberResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeamMemberAdminService {
    Long add(AddTeamMemberRequest request);

    void remove(RemoveTeamMemberRequest request);

    void updateRole(UpdateTeamMemberRoleRequest request);

    Page<TeamMemberResponse> search(SearchTeamMemberRequest request, Pageable pageable);
}
