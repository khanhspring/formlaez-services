package com.formlaez.service.admin.attachment;

import com.formlaez.application.model.request.CreateAttachmentRequest;
import com.formlaez.application.model.response.PresignedUrlResponse;

public interface AttachmentAdminService {
    Long create(CreateAttachmentRequest request);
    PresignedUrlResponse getDownloadUrl(String code);
}
