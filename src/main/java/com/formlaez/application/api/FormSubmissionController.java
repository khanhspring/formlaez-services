package com.formlaez.application.api;

import com.formlaez.application.model.request.CreateFormSubmissionRequest;
import com.formlaez.application.model.response.ResponseCode;
import com.formlaez.service.submission.FormSubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/forms/{formCode}/submissions")
public class FormSubmissionController {

    private final FormSubmissionService formSubmissionService;

    @PostMapping
    public ResponseCode create(@PathVariable String formCode,
                               @Valid @RequestBody CreateFormSubmissionRequest request) {
        request.setFormCode(formCode);
        return ResponseCode.of(formSubmissionService.create(request));
    }
}
