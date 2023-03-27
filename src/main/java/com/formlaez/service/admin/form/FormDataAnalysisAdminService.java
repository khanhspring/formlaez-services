package com.formlaez.service.admin.form;

import com.formlaez.application.model.response.form.analysis.FormDataAnalysisResponse;

public interface FormDataAnalysisAdminService {
    FormDataAnalysisResponse getAnalysis(String code);
}
