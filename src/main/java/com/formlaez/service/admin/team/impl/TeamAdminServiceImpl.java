package com.formlaez.service.admin.team.impl;

import com.formlaez.application.model.request.CreateTeamRequest;
import com.formlaez.application.model.request.SearchTeamRequest;
import com.formlaez.application.model.request.UpdateTeamRequest;
import com.formlaez.application.model.response.TeamResponse;
import com.formlaez.infrastructure.configuration.exception.ForbiddenException;
import com.formlaez.infrastructure.configuration.exception.InvalidParamsException;
import com.formlaez.infrastructure.configuration.exception.ResourceNotFoundException;
import com.formlaez.infrastructure.configuration.exception.UnauthorizedException;
import com.formlaez.infrastructure.converter.TeamResponseConverter;
import com.formlaez.infrastructure.enumeration.TeamMemberRole;
import com.formlaez.infrastructure.enumeration.WorkspaceMemberRole;
import com.formlaez.infrastructure.model.entity.team.JpaTeam;
import com.formlaez.infrastructure.model.entity.team.JpaTeamMember;
import com.formlaez.infrastructure.repository.*;
import com.formlaez.infrastructure.util.AuthUtils;
import com.formlaez.infrastructure.util.RandomUtils;
import com.formlaez.service.admin.team.TeamAdminService;
import com.formlaez.service.helper.TeamHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamAdminServiceImpl implements TeamAdminService {
    private final JpaTeamRepository jpaTeamRepository;
    private final JpaTeamMemberRepository jpaTeamMemberRepository;
    private final JpaUserRepository jpaUserRepository;
    private final JpaWorkspaceRepository jpaWorkspaceRepository;
    private final JpaWorkspaceMemberRepository jpaWorkspaceMemberRepository;
    private final TeamHelper teamHelper;
    private final TeamResponseConverter teamResponseConverter;

    @Override
    @Transactional(readOnly = true)
    public Page<TeamResponse> search(SearchTeamRequest request, Pageable pageable) {
        var currentUserId = AuthUtils.currentUserIdOrElseThrow();
        var isTeamMember = jpaWorkspaceMemberRepository.existsByUserIdAndWorkspaceId(currentUserId, request.getWorkspaceId());
        if (!isTeamMember) {
            throw new ForbiddenException();
        }

        request.setMemberId(currentUserId);
        return jpaTeamRepository.search(request, pageable)
                .map(teamResponseConverter::convert);
    }

    @Override
    @Transactional
    public Long create(CreateTeamRequest request) {
        // TODO check permission

        var workspace = jpaWorkspaceRepository.findById(request.getWorkspaceId())
                .orElseThrow(InvalidParamsException::new);

        var entity = JpaTeam.builder()
                .code(RandomUtils.randomNanoId(7))
                .description(request.getDescription())
                .name(request.getName())
                .workspace(workspace)
                .build();
        var team = jpaTeamRepository.save(entity);

        var currentUserId = AuthUtils.currentUserIdOrElseThrow();
        var currentUser = jpaUserRepository.findById(currentUserId)
                .orElseThrow(UnauthorizedException::new);

        var owner = JpaTeamMember.builder()
                .user(currentUser)
                .team(team)
                .role(TeamMemberRole.Owner)
                .build();

        jpaTeamMemberRepository.save(owner);
        return team.getId();
    }

    @Override
    public void update(UpdateTeamRequest request) {
        var existing = jpaTeamRepository.findById(request.getId())
                .orElseThrow(ResourceNotFoundException::new);

        teamHelper.currentUserMustBeOwner(request.getId());

        existing.setName(request.getName());
        existing.setDescription(request.getDescription());
        jpaTeamRepository.save(existing);
    }

    @Override
    public TeamResponse getById(Long id) {
        var existing = jpaTeamRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: check access
        return teamResponseConverter.convert(existing);
    }

    @Override
    public TeamResponse getByCode(String code) {
        var existing = jpaTeamRepository.findByCode(code)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: check access
        return teamResponseConverter.convert(existing);
    }
}
