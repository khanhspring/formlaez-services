package com.formlaez.service.admin.attachment.impl;

import com.formlaez.application.model.request.CreateAttachmentRequest;
import com.formlaez.application.model.response.PresignedUrlResponse;
import com.formlaez.infrastructure.configuration.exception.ResourceNotFoundException;
import com.formlaez.infrastructure.model.entity.JpaAttachment;
import com.formlaez.infrastructure.repository.JpaAttachmentRepository;
import com.formlaez.infrastructure.util.RandomUtils;
import com.formlaez.service.admin.attachment.AttachmentAdminService;
import com.formlaez.service.storage.CloudStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AttachmentAdminServiceImpl implements AttachmentAdminService {

    private static final int DOWNLOAD_URL_EXPIRATION_IN_MILLIS = 5000*60;
    private final JpaAttachmentRepository jpaAttachmentRepository;
    private final CloudStorageService cloudStorageService;

    @Override
    @Transactional
    public Long create(CreateAttachmentRequest request) {
        var attachment = JpaAttachment.builder()
                .code(RandomUtils.randomNanoId())
                .type(request.getType())
                .name(request.getName())
                .originalName(request.getOriginalName())
                .cloudStorageLocation(request.getCloudStorageLocation())
                .size(request.getSize())
                .extension(request.getExtension())
                .description(request.getDescription())
                .category(request.getCategory())
                .build();
        return jpaAttachmentRepository.save(attachment).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public PresignedUrlResponse getDownloadUrl(String code) {
        var attachment = jpaAttachmentRepository.findByCode(code)
                .orElseThrow(ResourceNotFoundException::new);

        var url = cloudStorageService.getDownloadUrl(attachment.getCloudStorageLocation(), attachment.getName(), DOWNLOAD_URL_EXPIRATION_IN_MILLIS);
        return PresignedUrlResponse.builder()
                .url(url)
                .build();
    }
}
