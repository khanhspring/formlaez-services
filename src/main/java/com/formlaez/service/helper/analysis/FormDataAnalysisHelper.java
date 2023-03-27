package com.formlaez.service.helper.analysis;

import com.formlaez.application.model.response.form.analysis.FormFieldAnalysisResponse;
import com.formlaez.infrastructure.model.entity.form.JpaForm;
import com.formlaez.infrastructure.model.entity.form.JpaFormField;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FormDataAnalysisHelper {

    private final List<FieldDataAnalyzer> analyzers;

    public FormFieldAnalysisResponse analyze(JpaForm form, JpaFormField field) {
        for (var analyzer : analyzers) {
            if (analyzer.isSupport(field.getType())) {
                try {
                    return analyzer.analyze(form, field);
                } catch (Exception e) {
                    log.warn("Error when analyze field [{}]", field.getCode());
                    return null;
                }
            }
        }
        return null;
    }
}
