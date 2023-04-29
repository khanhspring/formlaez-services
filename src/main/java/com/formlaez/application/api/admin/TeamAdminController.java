package com.formlaez.application.api.admin;

import com.formlaez.application.model.request.*;
import com.formlaez.application.model.response.ResponseId;
import com.formlaez.application.model.response.TeamMemberResponse;
import com.formlaez.application.model.response.TeamResponse;
import com.formlaez.service.admin.team.TeamAdminService;
import com.formlaez.service.admin.team.TeamMemberAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/admin/teams")
public class TeamAdminController {

    private final TeamAdminService teamAdminService;
    private final TeamMemberAdminService teamMemberAdminService;

    @GetMapping
    public Page<TeamResponse> search(@Valid SearchTeamRequest request,
                                     @PageableDefault(size = 20, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return teamAdminService.search(request, pageable);
    }

    @PostMapping
    public ResponseId<Long> create(@Valid @RequestBody CreateTeamRequest request) {
        return ResponseId.of(teamAdminService.create(request));
    }

    @PutMapping("{teamId}")
    public void update(@PathVariable Long teamId,
                       @Valid @RequestBody UpdateTeamRequest request) {
        request.setId(teamId);
        teamAdminService.update(request);
    }

    @GetMapping("{teamCode}")
    public TeamResponse getByCode(@PathVariable String teamCode) {
        return teamAdminService.getByCode(teamCode);
    }

    @PostMapping("{teamId}/members")
    public ResponseId<Long> addMember(@PathVariable Long teamId,
                                      @RequestBody @Valid AddTeamMemberRequest request) {
        request.setTeamId(teamId);
        return ResponseId.of(teamMemberAdminService.add(request));
    }

    @DeleteMapping("{teamId}/members/{userId}")
    public void addMember(@PathVariable Long teamId,
                          @PathVariable String userId) {
        var request = RemoveTeamMemberRequest.builder()
                .teamId(teamId)
                .userId(userId)
                .build();
        teamMemberAdminService.remove(request);
    }

    @GetMapping("{teamId}/members")
    public Page<TeamMemberResponse> search(@PathVariable Long teamId,
                                           SearchTeamMemberRequest request,
                                           @PageableDefault(size = 20, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        request.setTeamId(teamId);
        return teamMemberAdminService.search(request, pageable);
    }

    @PutMapping("{teamId}/members/{userId}")
    public void updateRole(@PathVariable Long teamId,
                                       @PathVariable String userId,
                                       @RequestBody @Valid UpdateTeamMemberRoleRequest request) {
        request.setTeamId(teamId);
        request.setUserId(userId);
        teamMemberAdminService.updateRole(request);
    }
}
