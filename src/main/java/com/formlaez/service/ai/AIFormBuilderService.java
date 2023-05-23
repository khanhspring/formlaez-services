package com.formlaez.service.ai;

import com.formlaez.application.model.request.AIFormRequest;
import com.formlaez.application.model.response.form.ai.AIFormResponse;

public interface AIFormBuilderService {
    AIFormResponse generate(AIFormRequest request);
}
