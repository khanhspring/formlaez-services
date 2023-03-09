package com.formlaez.application.api;

import com.formlaez.application.model.request.CreateFormRequest;
import com.formlaez.application.model.request.SearchFormRequest;
import com.formlaez.application.model.request.UpdateFormRequest;
import com.formlaez.application.model.response.form.BasicFormResponse;
import com.formlaez.application.model.response.ResponseId;
import com.formlaez.application.model.response.form.FormResponse;
import com.formlaez.service.form.FormService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/forms")
public class FormController {

    private final FormService formService;

    @GetMapping
    public Page<BasicFormResponse> search(@Valid SearchFormRequest request,
                                          @PageableDefault(size = 20, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return formService.search(request, pageable);
    }

    @GetMapping("{formCode}")
    public BasicFormResponse findByCode(@PathVariable String formCode) {
        return formService.findByCode(formCode);
    }

    @GetMapping("{formCode}/detail")
    public FormResponse getFormDetail(@PathVariable String formCode) {
        return formService.getDetail(formCode);
    }

    @PostMapping
    public ResponseId<Long> create(@Valid @RequestBody CreateFormRequest request) {
        return ResponseId.of(formService.create(request));
    }

    @PutMapping("{formId}")
    public void update(@PathVariable Long formId,
                       @Valid @RequestBody UpdateFormRequest request) {
        request.setId(formId);
        formService.update(request);
    }
}
