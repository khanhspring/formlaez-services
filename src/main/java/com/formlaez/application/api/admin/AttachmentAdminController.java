package com.formlaez.application.api.admin;

import com.formlaez.application.model.response.PresignedUrlResponse;
import com.formlaez.service.admin.attachment.AttachmentAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/admin/attachments")
public class AttachmentAdminController {

    private final AttachmentAdminService attachmentAdminService;

    @GetMapping("{code}/download-url")
    public PresignedUrlResponse getDownloadUrl(@PathVariable String code) {
        return attachmentAdminService.getDownloadUrl(code);
    }

}
