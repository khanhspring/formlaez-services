package com.formlaez.service.admin.workspace.impl;

import com.formlaez.application.model.request.UpdateOpenAIApiSettingRequest;
import com.formlaez.application.model.response.WorkspaceOpenAIApiResponse;
import com.formlaez.infrastructure.model.entity.JpaOpenaiApiSetting;
import com.formlaez.infrastructure.repository.JpaOpenaiApiSettingRepository;
import com.formlaez.infrastructure.repository.JpaWorkspaceRepository;
import com.formlaez.service.admin.workspace.WorkspaceOpenApiSettingAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkspaceOpenApiSettingAdminServiceImpl implements WorkspaceOpenApiSettingAdminService {

    private final JpaOpenaiApiSettingRepository jpaOpenaiApiSettingRepository;
    private final JpaWorkspaceRepository jpaWorkspaceRepository;

    @Override
    @Transactional
    public void update(UpdateOpenAIApiSettingRequest request) {
        var workspace = jpaWorkspaceRepository.findById(request.getWorkspaceId())
                .orElseThrow();
        var openApiSetting = jpaOpenaiApiSettingRepository.findByWorkspaceId(request.getWorkspaceId())
                .orElse(JpaOpenaiApiSetting.builder()
                        .workspace(workspace)
                        .build());

        openApiSetting.setApiKey(request.getApiKey());
        openApiSetting.setModel(request.getModel());
        jpaOpenaiApiSettingRepository.save(openApiSetting);
    }

    @Override
    @Transactional(readOnly = true)
    public WorkspaceOpenAIApiResponse get(Long workspaceId) {
        var openApiSetting = jpaOpenaiApiSettingRepository.findByWorkspaceId(workspaceId)
                .orElse(new JpaOpenaiApiSetting());
        return WorkspaceOpenAIApiResponse.builder()
                .apiKey(openApiSetting.getApiKey())
                .model(openApiSetting.getModel())
                .build();
    }
}
