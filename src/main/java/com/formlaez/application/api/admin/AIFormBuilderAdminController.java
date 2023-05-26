package com.formlaez.application.api.admin;

import com.formlaez.application.model.request.AIFormRequest;
import com.formlaez.application.model.response.form.ai.AIFormResponse;
import com.formlaez.service.ai.AIFormBuilderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/admin/forms")
public class AIFormBuilderAdminController {

    private final AIFormBuilderService aiFormBuilderService;

    @PostMapping(path = "ai/generate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AIFormResponse generate(@Valid AIFormRequest request) {
        return aiFormBuilderService.generate(request);
    }
}
