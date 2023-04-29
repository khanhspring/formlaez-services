package com.formlaez.application.api;

import com.formlaez.application.model.response.form.BasicFormResponse;
import com.formlaez.application.model.response.form.FormResponse;
import com.formlaez.service.form.FormService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/forms")
public class FormController {

    private final FormService formService;

    @GetMapping("{formCode}")
    public BasicFormResponse findByCode(@PathVariable String formCode) {
        return formService.findByCode(formCode);
    }

    @GetMapping("{formCode}/detail")
    public FormResponse getFormDetail(@PathVariable String formCode) {
        return formService.getDetail(formCode);
    }
}
