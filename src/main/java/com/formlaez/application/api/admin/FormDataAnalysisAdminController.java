package com.formlaez.application.api.admin;

import com.formlaez.application.model.response.form.analysis.FormDataAnalysisResponse;
import com.formlaez.service.admin.form.FormDataAnalysisAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/admin/forms")
public class FormDataAnalysisAdminController {

    private final FormDataAnalysisAdminService formDataAnalysisAdminService;

    @GetMapping("{formCode}/analysis")
    public FormDataAnalysisResponse getAnalysis(@PathVariable String formCode) {
        return formDataAnalysisAdminService.getAnalysis(formCode);
    }
}
