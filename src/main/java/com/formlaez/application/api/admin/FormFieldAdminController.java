package com.formlaez.application.api.admin;

import com.formlaez.application.model.request.*;
import com.formlaez.application.model.response.ResponseId;
import com.formlaez.service.admin.form.FormFieldAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/admin/forms/fields")
public class FormFieldAdminController {

    private final FormFieldAdminService formFieldAdminService;

    @PostMapping
    public ResponseId<Long> create(@Valid @RequestBody CreateFormFieldRequest request) {
        return ResponseId.of(formFieldAdminService.create(request));
    }

    @PutMapping("{fieldCode}")
    public void update(@PathVariable String fieldCode, @Valid @RequestBody UpdateFormFieldRequest request) {
        request.setCode(fieldCode);
        formFieldAdminService.update(request);
    }

    @PostMapping("{fieldCode}/move")
    public void move(@PathVariable String fieldCode,
                     @Valid @RequestBody MoveFormFieldRequest request) {
        request.setFieldCode(fieldCode);
        formFieldAdminService.move(request);
    }

    @DeleteMapping("{fieldCode}")
    public void remove(@PathVariable String fieldCode) {
        formFieldAdminService.remove(fieldCode);
    }
}
