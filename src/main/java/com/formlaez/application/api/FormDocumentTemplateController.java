package com.formlaez.application.api;

import com.formlaez.application.model.response.FormDocumentTemplateResponse;
import com.formlaez.service.form.FormDocumentTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/document-templates")
public class FormDocumentTemplateController {

    private final FormDocumentTemplateService formDocumentTemplateService;

    @GetMapping
    public List<FormDocumentTemplateResponse> search(@RequestParam("formId") Long formId) {
        return formDocumentTemplateService.getByFormId(formId);
    }
}
