package com.formlaez.application.api.admin;

import com.formlaez.application.model.request.CreateFormRequest;
import com.formlaez.application.model.request.SearchFormRequest;
import com.formlaez.application.model.request.UpdateFormRequest;
import com.formlaez.application.model.request.UpdateFormSettingsRequest;
import com.formlaez.application.model.response.ResponseCode;
import com.formlaez.application.model.response.ResponseId;
import com.formlaez.application.model.response.form.BasicFormResponse;
import com.formlaez.application.model.response.form.FormResponse;
import com.formlaez.service.admin.form.FormAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/admin/forms")
public class FormAdminController {

    private final FormAdminService formAdminService;

    @GetMapping
    public Page<BasicFormResponse> search(@Valid SearchFormRequest request,
                                          @PageableDefault(size = 20, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return formAdminService.search(request, pageable);
    }

    @GetMapping("{formCode}")
    public BasicFormResponse findByCode(@PathVariable String formCode) {
        return formAdminService.findByCode(formCode);
    }

    @GetMapping("{formCode}/detail")
    public FormResponse getFormDetail(@PathVariable String formCode) {
        return formAdminService.getDetail(formCode);
    }

    @PostMapping
    public ResponseCode create(@Valid @RequestBody CreateFormRequest request) {
        return ResponseCode.of(formAdminService.create(request));
    }

    @PutMapping("{formId}")
    public void update(@PathVariable Long formId,
                       @Valid @RequestBody UpdateFormRequest request) {
        request.setId(formId);
        formAdminService.update(request);
    }

    @PutMapping("{formId}/settings")
    public void updateSettings(@PathVariable Long formId,
                               @Valid @RequestBody UpdateFormSettingsRequest request) {
        request.setId(formId);
        formAdminService.updateSettings(request);
    }

    @PostMapping("{formId}/publish")
    public void publish(@PathVariable Long formId) {
        formAdminService.publish(formId);
    }

    @PostMapping("{formId}/archive")
    public void archive(@PathVariable Long formId) {
        formAdminService.archive(formId);
    }

    @DeleteMapping("{formId}")
    public void delete(@PathVariable Long formId) {
        formAdminService.delete(formId);
    }
}
