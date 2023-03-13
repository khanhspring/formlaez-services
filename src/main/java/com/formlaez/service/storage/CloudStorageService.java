package com.formlaez.service.storage;

import com.formlaez.infrastructure.model.common.attachment.AttachmentMetadata;

import java.io.InputStream;

public interface CloudStorageService {
    void upload(InputStream inputStream, AttachmentMetadata info);
    InputStream download(String cloudStorageLocation, String fileName);
    String getDownloadUrl(String cloudStorageLocation, String fileName, int expiredInMillis);
}
