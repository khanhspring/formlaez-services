package com.formlaez.application.api;

import com.formlaez.application.model.request.*;
import com.formlaez.application.model.response.ResponseId;
import com.formlaez.service.form.FormFieldService;
import com.formlaez.service.form.FormSectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/forms/fields")
public class FormFieldController {

    private final FormFieldService formFieldService;

    @PostMapping
    public ResponseId<Long> create(@Valid @RequestBody CreateFormFieldRequest request) {
        return ResponseId.of(formFieldService.create(request));
    }

    @PutMapping("{fieldCode}")
    public void update(@PathVariable String fieldCode, @Valid @RequestBody UpdateFormFieldRequest request) {
        request.setCode(fieldCode);
        formFieldService.update(request);
    }

    @PostMapping("{fieldCode}/move")
    public void move(@PathVariable String fieldCode,
                     @Valid @RequestBody MoveFormFieldRequest request) {
        request.setFieldCode(fieldCode);
        formFieldService.move(request);
    }
}
