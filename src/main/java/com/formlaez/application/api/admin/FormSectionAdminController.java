package com.formlaez.application.api.admin;

import com.formlaez.application.model.request.CreateFormSectionRequest;
import com.formlaez.application.model.request.MoveFormSectionRequest;
import com.formlaez.application.model.request.UpdateFormSectionRequest;
import com.formlaez.application.model.response.ResponseId;
import com.formlaez.service.admin.form.FormSectionAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/admin/forms/sections")
public class FormSectionAdminController {

    private final FormSectionAdminService formSectionAdminService;

    @PostMapping
    public ResponseId<Long> create(@Valid @RequestBody CreateFormSectionRequest request) {
        return ResponseId.of(formSectionAdminService.create(request));
    }

    @PutMapping("{sectionCode}")
    public void update(@PathVariable String sectionCode,
                       @Valid @RequestBody UpdateFormSectionRequest request) {
        request.setCode(sectionCode);
        formSectionAdminService.update(request);
    }

    @PostMapping("{sectionCode}/move")
    public void move(@PathVariable String sectionCode,
                     @Valid @RequestBody MoveFormSectionRequest request) {
        request.setSectionCode(sectionCode);
        formSectionAdminService.move(request);
    }

    @DeleteMapping("{sectionCode}")
    public void remove(@PathVariable String sectionCode) {
        formSectionAdminService.remove(sectionCode);
    }
}
