package com.formlaez.application.api;

import com.formlaez.application.model.request.CreateFormSubmissionRequest;
import com.formlaez.application.model.request.MergeDocumentFormSubmissionRequest;
import com.formlaez.application.model.response.ResponseCode;
import com.formlaez.service.submission.FormSubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping()
public class FormSubmissionController {

    private final FormSubmissionService formSubmissionService;

    @PostMapping("/forms/{formCode}/submissions")
    public ResponseCode create(@PathVariable String formCode,
                               @Valid @RequestBody CreateFormSubmissionRequest request) {
        request.setFormCode(formCode);
        return ResponseCode.of(formSubmissionService.create(request));
    }

    @PostMapping("/submissions/{submissionCode}/document-merge")
    public ResponseEntity<Resource> mergeDocument(@PathVariable String submissionCode,
                                                  @RequestBody @Valid MergeDocumentFormSubmissionRequest request) {
        request.setCode(submissionCode);
        var result = formSubmissionService.mergeDocument(request);
        var resource = new ByteArrayResource(result);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; file=export.docx")
                .contentLength(result.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
