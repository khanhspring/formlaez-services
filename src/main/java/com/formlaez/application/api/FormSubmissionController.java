package com.formlaez.application.api;

import com.formlaez.application.model.request.AdvanceSearchFormSubmissionRequest;
import com.formlaez.application.model.request.CreateFormSubmissionRequest;
import com.formlaez.application.model.request.SearchFormSubmissionRequest;
import com.formlaez.application.model.response.ResponseCode;
import com.formlaez.application.model.response.form.FormSubmissionResponse;
import com.formlaez.service.submission.FormSubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @GetMapping
    public Page<FormSubmissionResponse> search(@PathVariable String formCode,
                                               SearchFormSubmissionRequest request,
                                               @PageableDefault(size = 20, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        request.setFormCode(formCode);
        return formSubmissionService.search(request, pageable);
    }

    @GetMapping(params = "keywords")
    public Page<FormSubmissionResponse> searchAdvance(@PathVariable String formCode,
                                                      AdvanceSearchFormSubmissionRequest request,
                                                      @PageableDefault(size = 20, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        request.setFormCode(formCode);
        return formSubmissionService.searchAdvance(request, pageable);
    }
}
