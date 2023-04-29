package com.formlaez.service.admin.form.impl;

import com.formlaez.application.model.request.CreateFormRequest;
import com.formlaez.application.model.request.SearchFormRequest;
import com.formlaez.application.model.request.UpdateFormRequest;
import com.formlaez.application.model.request.UpdateFormSettingsRequest;
import com.formlaez.application.model.response.form.BasicFormResponse;
import com.formlaez.application.model.response.form.FormResponse;
import com.formlaez.infrastructure.configuration.exception.ForbiddenException;
import com.formlaez.infrastructure.configuration.exception.InvalidParamsException;
import com.formlaez.infrastructure.configuration.exception.ResourceNotFoundException;
import com.formlaez.infrastructure.converter.BasicFormResponseConverter;
import com.formlaez.infrastructure.converter.FormResponseConverter;
import com.formlaez.infrastructure.enumeration.FormScope;
import com.formlaez.infrastructure.enumeration.FormSharingScope;
import com.formlaez.infrastructure.enumeration.FormStatus;
import com.formlaez.infrastructure.model.entity.form.JpaForm;
import com.formlaez.infrastructure.model.entity.form.JpaFormPage;
import com.formlaez.infrastructure.model.entity.team.JpaTeam;
import com.formlaez.infrastructure.repository.*;
import com.formlaez.infrastructure.util.AuthUtils;
import com.formlaez.infrastructure.util.RandomUtils;
import com.formlaez.service.admin.form.FormAdminService;
import com.formlaez.service.helper.FormAdminAccessHelper;
import com.formlaez.service.usage.WorkspaceUsageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import static com.formlaez.infrastructure.enumeration.FormStatus.Deleted;

@Slf4j
@Service
@RequiredArgsConstructor
public class FormAdminServiceImpl implements FormAdminService {

    private final JpaFormRepository jpaFormRepository;
    private final JpaFormPageRepository jpaFormPageRepository;
    private final JpaTeamRepository jpaTeamRepository;
    private final JpaWorkspaceRepository jpaWorkspaceRepository;
    private final JpaWorkspaceMemberRepository jpaWorkspaceMemberRepository;
    private final JpaTeamMemberRepository jpaTeamMemberRepository;
    private final FormResponseConverter formResponseConverter;
    private final BasicFormResponseConverter basicFormResponseConverter;
    private final FormAdminAccessHelper formAdminAccessHelper;
    private final WorkspaceUsageService workspaceUsageService;

    @Override
    @Transactional(readOnly = true)
    public Page<BasicFormResponse> search(SearchFormRequest request, Pageable pageable) {
        if (request.getScope() == FormScope.Private) {
            request.setCreatedBy(AuthUtils.currentUserIdOrElseThrow());
        }
        if (request.getScope() == FormScope.Team) {
            Assert.notNull(request.getTeamId(), "Team id is required");
        }
        return jpaFormRepository.search(request, pageable)
                .map(basicFormResponseConverter::convert);
    }

    @Override
    @Transactional(readOnly = true)
    public BasicFormResponse findByCode(String code) {
        var form = jpaFormRepository.findByCodeAndStatusNot(code, Deleted)
                .orElseThrow(ResourceNotFoundException::new);
        formAdminAccessHelper.checkAccess(form);
        return basicFormResponseConverter.convert(form);
    }

    @Override
    @Transactional(readOnly = true)
    public FormResponse getDetail(String code) {
        var form = jpaFormRepository.findByCodeAndStatusNot(code, Deleted)
                .orElseThrow(ResourceNotFoundException::new);
        formAdminAccessHelper.checkAccess(form);
        return formResponseConverter.convert(form);
    }

    @Override
    @Transactional
    public String create(CreateFormRequest request) {
        workspaceUsageService.checkFormLimitAndIncreaseOrElseThrow(request.getWorkspaceId());
        var workspace = jpaWorkspaceRepository.findById(request.getWorkspaceId())
                .orElseThrow(InvalidParamsException::new);

        var currentUserId = AuthUtils.currentUserIdOrElseThrow();

        JpaTeam team = null;
        if (request.getScope() == FormScope.Team) {
            Assert.notNull(request.getTeamId(), "Team id is required");
            team = jpaTeamRepository.findById(request.getTeamId())
                    .orElseThrow(InvalidParamsException::new);

            Assert.isTrue(workspace.getId().equals(team.getWorkspace().getId()), "Team id is not belong to workspace id");

            var isTeamMember = jpaTeamMemberRepository.existsByUserIdAndTeamId(currentUserId, team.getId());
            if (!isTeamMember) {
                log.error("User is not a member of the team, user id [{}], team id [{}]", currentUserId, team.getId());
                throw new ForbiddenException();
            }
        }

        if (request.getScope() == FormScope.Private) {
            var isWorkspaceMember = jpaWorkspaceMemberRepository.existsByUserIdAndWorkspaceId(currentUserId, workspace.getId());
            if (!isWorkspaceMember) {
                log.error("User is not a member of the workspace, user id [{}], workspace id [{}]", currentUserId, workspace.getId());
                throw new ForbiddenException();
            }
        }

        var form = JpaForm.builder()
                .code(RandomUtils.randomNanoId())
                .scope(request.getScope())
                .title(request.getTitle())
                .description(request.getDescription())
                .coverType(request.getCoverType())
                .coverColor(request.getCoverColor())
                .coverImageUrl(request.getCoverImageUrl())
                .status(FormStatus.Draft)
                .sharingScope(FormSharingScope.Public)
                .allowPrinting(false)
                .allowResponseEditing(false)
                .acceptResponses(true)
                .team(team)
                .workspace(workspace)
                .build();
        form = jpaFormRepository.save(form);

        // init default page
        var firstPage = JpaFormPage.builder()
                .code(RandomUtils.randomNanoId())
                .title("Untitled Page")
                .position(0)
                .form(form)
                .build();
        jpaFormPageRepository.save(firstPage);

        return form.getCode();
    }

    @Override
    @Transactional
    public void update(UpdateFormRequest request) {
        var form = jpaFormRepository.findByIdAndStatusNot(request.getId(), Deleted)
                .orElseThrow(ResourceNotFoundException::new);

        Assert.isTrue(form.getStatus() != FormStatus.Archived, "Archived form can not be changed");

        form.setTitle(request.getTitle());
        form.setDescription(request.getDescription());
        form.setCoverType(request.getCoverType());
        form.setCoverColor(request.getCoverColor());
        form.setCoverImageUrl(request.getCoverImageUrl());
        jpaFormRepository.save(form);
    }

    @Override
    public void updateSettings(UpdateFormSettingsRequest request) {
        var form = jpaFormRepository.findByIdAndStatusNot(request.getId(), Deleted)
                .orElseThrow(ResourceNotFoundException::new);

        Assert.isTrue(form.getStatus() != FormStatus.Archived, "Archived form can not be changed");

        form.setSharingScope(request.getSharingScope());
        form.setAcceptResponses(request.isAcceptResponses());
        form.setAllowPrinting(request.isAllowPrinting());
        form.setAllowResponseEditing(request.isAllowResponseEditing());
        jpaFormRepository.save(form);
    }

    @Override
    @Transactional
    public void publish(Long id) {
        var form = jpaFormRepository.findByIdAndStatusNot(id, Deleted)
                .orElseThrow(ResourceNotFoundException::new);
        if (form.getStatus() == FormStatus.Published) {
            return;
        }
        form.setStatus(FormStatus.Published);
        jpaFormRepository.save(form);
    }

    @Override
    @Transactional
    public void archive(Long id) {
        var form = jpaFormRepository.findByIdAndStatusNot(id, Deleted)
                .orElseThrow(ResourceNotFoundException::new);
        if (form.getStatus() == FormStatus.Archived) {
            return;
        }
        form.setStatus(FormStatus.Archived);
        jpaFormRepository.save(form);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        var form = jpaFormRepository.findByIdAndStatusNot(id, Deleted)
                .orElseThrow(ResourceNotFoundException::new);

        workspaceUsageService.decreaseForm(form.getWorkspace().getId());
        form.setStatus(Deleted);
        jpaFormRepository.save(form);
    }
}
