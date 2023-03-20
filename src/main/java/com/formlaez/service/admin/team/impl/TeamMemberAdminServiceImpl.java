package com.formlaez.service.admin.team.impl;

import com.formlaez.application.model.request.AddTeamMemberRequest;
import com.formlaez.application.model.request.RemoveTeamMemberRequest;
import com.formlaez.application.model.request.SearchTeamMemberRequest;
import com.formlaez.application.model.request.UpdateTeamMemberRoleRequest;
import com.formlaez.application.model.response.TeamMemberResponse;
import com.formlaez.infrastructure.configuration.exception.ApplicationException;
import com.formlaez.infrastructure.configuration.exception.InvalidParamsException;
import com.formlaez.infrastructure.configuration.exception.ResourceNotFoundException;
import com.formlaez.infrastructure.converter.TeamMemberResponseConverter;
import com.formlaez.infrastructure.enumeration.TeamMemberRole;
import com.formlaez.infrastructure.enumeration.WorkspaceMemberRole;
import com.formlaez.infrastructure.model.entity.team.JpaTeamMember;
import com.formlaez.infrastructure.repository.JpaTeamMemberRepository;
import com.formlaez.infrastructure.repository.JpaTeamRepository;
import com.formlaez.infrastructure.repository.JpaUserRepository;
import com.formlaez.infrastructure.util.AuthUtils;
import com.formlaez.service.admin.team.TeamMemberAdminService;
import com.formlaez.service.helper.TeamHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import static com.formlaez.infrastructure.enumeration.TeamMemberRole.Member;
import static com.formlaez.infrastructure.enumeration.TeamMemberRole.Owner;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamMemberAdminServiceImpl implements TeamMemberAdminService {

    private final JpaTeamMemberRepository jpaTeamMemberRepository;
    private final JpaTeamRepository jpaTeamRepository;
    private final JpaUserRepository jpaUserRepository;
    private final TeamMemberResponseConverter teamMemberResponseConverter;
    private final TeamHelper teamHelper;

    @Override
    public Long add(AddTeamMemberRequest request) {
        teamHelper.currentUserMustBeOwner(request.getTeamId());

        var team = jpaTeamRepository.findById(request.getTeamId())
                .orElseThrow(InvalidParamsException::new);
        var user = jpaUserRepository.findById(request.getUserId())
                .orElseThrow(InvalidParamsException::new);

        var member = JpaTeamMember.builder()
                .user(user)
                .team(team)
                .role(request.getRole())
                .build();
        return jpaTeamMemberRepository.save(member)
                .getId();
    }

    @Override
    public void remove(RemoveTeamMemberRequest request) {
        teamHelper.currentUserMustBeOwner(request.getTeamId());

        var existing = jpaTeamMemberRepository.findByUserIdAndTeamId(request.getUserId(), request.getTeamId());
        if (existing.isEmpty()) {
            return;
        }

        var member = existing.get();

        if (member.getRole() == Owner) {
            var currentUserId = AuthUtils.currentUserIdOrElseThrow();
            var hasOtherAdmin = jpaTeamMemberRepository.existsByRoleAndTeamIdAndUserIdNot(TeamMemberRole.Owner, request.getTeamId(), currentUserId);
            if (!hasOtherAdmin) {
                log.error("Can not remove last admin of the team id [{}]", request.getTeamId());
                throw new InvalidParamsException();
            }
        }
        jpaTeamMemberRepository.delete(member);
    }

    @Override
    public void updateRole(UpdateTeamMemberRoleRequest request) {
        teamHelper.currentUserMustBeOwner(request.getTeamId());

        var existing = jpaTeamMemberRepository.findByUserIdAndTeamId(request.getUserId(), request.getTeamId())
                .orElseThrow(ResourceNotFoundException::new);

        if (existing.getRole() == Owner && request.getRole() == Member) {
            var currentUserId = AuthUtils.currentUserIdOrElseThrow();
            var hasOtherAdmin = jpaTeamMemberRepository.existsByRoleAndTeamIdAndUserIdNot(TeamMemberRole.Owner, request.getTeamId(), currentUserId);
            if (!hasOtherAdmin) {
                log.error("Can not remove last admin of the team id [{}]", request.getTeamId());
                throw new InvalidParamsException();
            }
        }

        existing.setRole(request.getRole());
        jpaTeamMemberRepository.save(existing);
    }

    @Override
    public Page<TeamMemberResponse> search(SearchTeamMemberRequest request, Pageable pageable) {
        var memberPage = jpaTeamMemberRepository.findByTeamId(request.getTeamId(), pageable);
        return memberPage.map(teamMemberResponseConverter::convert);
    }
}
