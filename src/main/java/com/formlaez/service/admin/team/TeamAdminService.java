package com.formlaez.service.admin.team;

import com.formlaez.application.model.request.CreateTeamRequest;
import com.formlaez.application.model.request.SearchTeamRequest;
import com.formlaez.application.model.request.UpdateTeamRequest;
import com.formlaez.application.model.response.TeamResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeamAdminService {
    Page<TeamResponse> search(SearchTeamRequest request, Pageable pageable);
    Long create(CreateTeamRequest request);

    void update(UpdateTeamRequest request);

    TeamResponse getById(Long id);
    TeamResponse getByCode(String code);
}
