package com.formlaez.application.api.admin;

import com.formlaez.application.model.request.*;
import com.formlaez.application.model.response.form.FormSubmissionResponse;
import com.formlaez.service.admin.submission.FormSubmissionAdminService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/admin/forms")
public class FormSubmissionAdminController {

    private final FormSubmissionAdminService formSubmissionAdminService;


    @GetMapping("{formCode}/submissions")
    public Page<FormSubmissionResponse> search(@PathVariable String formCode,
                                               SearchFormSubmissionRequest request,
                                               @PageableDefault(size = 20, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        request.setFormCode(formCode);
        return formSubmissionAdminService.search(request, pageable);
    }

    @GetMapping(value = "{formCode}/submissions", params = "keywords")
    public Page<FormSubmissionResponse> searchAdvance(@PathVariable String formCode,
                                                      AdvanceSearchFormSubmissionRequest request,
                                                      @PageableDefault(size = 20, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        request.setFormCode(formCode);
        return formSubmissionAdminService.searchAdvance(request, pageable);
    }

    @PutMapping("submissions/{submissionCode}")
    public void update(@PathVariable String submissionCode,
                       @Valid @RequestBody UpdateFormSubmissionRequest request) {
        request.setCode(submissionCode);
        formSubmissionAdminService.update(request);
    }

    @PostMapping("submissions/{submissionCode}/archive")
    public void archive(@PathVariable String submissionCode) {
        formSubmissionAdminService.archive(submissionCode);
    }

    @PostMapping("submissions/{submissionCode}/document-merge")
    public ResponseEntity<Resource> mergeDocument(@PathVariable String submissionCode,
                                                  @RequestBody @Valid MergeDocumentFormSubmissionRequest request) {
        request.setCode(submissionCode);
        var result = formSubmissionAdminService.mergeDocument(request);
        var resource = new ByteArrayResource(result);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; file=export.docx")
                .contentLength(result.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @PostMapping("{formCode}/submissions/export")
    public void export(@PathVariable String formCode, HttpServletResponse response) throws IOException {
        var request = new ExportFormSubmissionRequest();
        response.setContentType("text/csv; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; file=export.csv");

        request.setFormCode(formCode);
        formSubmissionAdminService.export(request, response.getWriter());
    }
}
