package com.formlaez.application.api;

import com.formlaez.application.model.request.CreateFormSectionRequest;
import com.formlaez.application.model.request.MoveFormSectionRequest;
import com.formlaez.application.model.response.ResponseId;
import com.formlaez.service.form.FormSectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/forms/sections")
public class FormSectionController {

    private final FormSectionService formSectionService;

    @PostMapping
    public ResponseId<Long> create(@Valid @RequestBody CreateFormSectionRequest request) {
        return ResponseId.of(formSectionService.create(request));
    }

    @PostMapping("{sectionCode}/move")
    public void move(@PathVariable String sectionCode,
                     @Valid @RequestBody MoveFormSectionRequest request) {
        request.setSectionCode(sectionCode);
        formSectionService.move(request);
    }
}
