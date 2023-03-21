package com.formlaez.application.api.admin;

import com.formlaez.application.model.request.CreateFormDocumentTemplateRequest;
import com.formlaez.application.model.request.SearchFormDocumentTemplateRequest;
import com.formlaez.application.model.request.UpdateFormDocumentTemplateRequest;
import com.formlaez.application.model.response.FormDocumentTemplateResponse;
import com.formlaez.application.model.response.ResponseId;
import com.formlaez.service.admin.form.FormDocumentTemplateAdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/admin/document-templates")
public class FormDocumentTemplateAdminController {

    private final FormDocumentTemplateAdminService formDocumentTemplateAdminService;

    @GetMapping
    public Page<FormDocumentTemplateResponse> search(@Valid SearchFormDocumentTemplateRequest request,
                                                     @PageableDefault(size = 20, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return formDocumentTemplateAdminService.search(request, pageable);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseId<Long> create(@Valid CreateFormDocumentTemplateRequest request) {
        return ResponseId.of(formDocumentTemplateAdminService.create(request));
    }

    @PutMapping("{id}")
    public void update(@PathVariable Long id,
                       @RequestBody @Valid UpdateFormDocumentTemplateRequest request) {
        request.setId(id);
        formDocumentTemplateAdminService.update(request);
    }

    @DeleteMapping("{id}")
    public void remove(@PathVariable Long id) {
        formDocumentTemplateAdminService.remove(id);
    }
}
