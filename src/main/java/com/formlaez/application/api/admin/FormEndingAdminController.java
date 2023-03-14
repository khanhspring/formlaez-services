package com.formlaez.application.api.admin;

import com.formlaez.application.model.request.UpdateFormEndingRequest;
import com.formlaez.application.model.response.form.FormEndingResponse;
import com.formlaez.service.admin.form.FormEndingAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/admin/forms/{formId}/ending")
public class FormEndingAdminController {

    private final FormEndingAdminService formEndingAdminService;

    @PostMapping
    public void upsert(@PathVariable Long formId,
                       @Valid @RequestBody UpdateFormEndingRequest request) {
        request.setFormId(formId);
        formEndingAdminService.upsert(request);
    }

    @GetMapping
    public FormEndingResponse get(@PathVariable Long formId) {
        return formEndingAdminService.getByFormId(formId);
    }
}
