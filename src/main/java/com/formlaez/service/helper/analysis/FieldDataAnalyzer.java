package com.formlaez.service.helper.analysis;

import com.formlaez.application.model.response.form.analysis.FormFieldAnalysisResponse;
import com.formlaez.infrastructure.enumeration.FormFieldType;
import com.formlaez.infrastructure.model.entity.form.JpaForm;
import com.formlaez.infrastructure.model.entity.form.JpaFormField;

public interface FieldDataAnalyzer {
    FormFieldAnalysisResponse analyze(JpaForm form, JpaFormField field);
    boolean isSupport(FormFieldType type);
}
