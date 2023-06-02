package com.formlaez.service.ai.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.formlaez.application.model.request.AIFormRequest;
import com.formlaez.application.model.response.form.ai.AIFormResponse;
import com.formlaez.infrastructure.configuration.exception.ApplicationException;
import com.formlaez.infrastructure.configuration.exception.CommonError;
import com.formlaez.infrastructure.configuration.exception.ResourceNotFoundException;
import com.formlaez.infrastructure.enumeration.ChatGPTPrompt;
import com.formlaez.infrastructure.model.common.ai.OpenaiConfig;
import com.formlaez.infrastructure.repository.JpaFormRepository;
import com.formlaez.infrastructure.repository.JpaOpenaiApiSettingRepository;
import com.formlaez.infrastructure.util.MsWordUtils;
import com.formlaez.infrastructure.util.PdfUtils;
import com.formlaez.service.ai.AIFormBuilderService;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AIFormBuilderServiceImpl implements AIFormBuilderService {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(180);
    private static final String DEFAULT_MODEL = "gpt-3.5-turbo";

    private final JpaOpenaiApiSettingRepository jpaOpenaiApiSettingRepository;
    private final JpaFormRepository jpaFormRepository;
    private final ObjectMapper objectMapper;

    @Override
    public AIFormResponse generate(AIFormRequest request) {
        var form = jpaFormRepository.findById(request.getFormId())
                .orElseThrow(ResourceNotFoundException::new);

        OpenaiConfig config;
        if (!ObjectUtils.isEmpty(request.getApiKey())) {
            config = initConfig(request.getApiKey(), request.getApiModel());
        } else {
            var apiSetting = jpaOpenaiApiSettingRepository.findByWorkspaceId(form.getWorkspace().getId())
                    .filter(s -> !ObjectUtils.isEmpty(s.getApiKey()))
                    .orElseThrow(() -> new ApplicationException(CommonError.OpenAIApiNotfound));

            config = initConfig(apiSetting.getApiKey(), apiSetting.getModel());
        }

        var aiResponse = askChatGPT(config, request);
        if (ObjectUtils.isEmpty(aiResponse)) {
            return null;
        }

        return convertResponseToObject(aiResponse);
    }

    private String askChatGPT(OpenaiConfig config, AIFormRequest request) {
        var service = new OpenAiService(config.getApiKey(), DEFAULT_TIMEOUT);
        var initMessage = new ChatMessage("system", ChatGPTPrompt.FormJSONFormat.getContent());
        var userMessage = new ChatMessage("user", request.getMessage());

        List<ChatMessage> messages = new ArrayList<>();
        messages.add(initMessage);
        messages.add(userMessage);

        if (Objects.nonNull(request.getFile())) {
            var originalName = request.getFile().getOriginalFilename();
            var extension = FilenameUtils.getExtension(originalName);
            String fileContent;
            if ("pdf".equalsIgnoreCase(extension)) {
                fileContent = PdfUtils.extractRawText(request.getFile());
            } else {
                fileContent = MsWordUtils.extractRawText(request.getFile());
            }
            var fileContentMessage = new ChatMessage("user", fileContent);
            messages.add(fileContentMessage);
        }

        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .model(config.getModel())
                .messages(messages)
                .build();
        ChatCompletionResult response;
        try {
            response = service.createChatCompletion(completionRequest);
        } catch (Exception e) {
            throw new ApplicationException(CommonError.OpenAIApiError, e.getMessage());
        }
        if (CollectionUtils.isEmpty(response.getChoices())) {
            return null;
        }
        if (Objects.isNull(response.getChoices().get(0).getMessage())) {
            return null;
        }
        return response.getChoices().get(0).getMessage().getContent();
    }

    private OpenaiConfig initConfig(String apiKey, String model) {
        if (ObjectUtils.isEmpty(apiKey)) {
            throw new ApplicationException(CommonError.OpenAIApiNotfound);
        }
        return OpenaiConfig.builder()
                .apiKey(apiKey)
                .model(ObjectUtils.isEmpty(model) ? DEFAULT_MODEL : model)
                .build();
    }

    private AIFormResponse convertResponseToObject(String aiResponse) {
        try {
            return objectMapper.readValue(aiResponse, AIFormResponse.class);
        } catch (Exception e) {
            log.warn("Can not parse ai response to object", e);
        }
        return null;
    }
}
