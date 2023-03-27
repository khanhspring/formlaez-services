package com.formlaez.service.storage.impl;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.formlaez.infrastructure.model.common.attachment.AttachmentMetadata;
import com.formlaez.infrastructure.property.aws.AwsProperties;
import com.formlaez.service.storage.CloudStorageService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AwsS3CloudStorageServiceImpl implements CloudStorageService {

    private final AmazonS3 amazonS3;
    private final AwsProperties properties;

    @Override
    public void upload(InputStream inputStream, AttachmentMetadata info) {
        var key = String.format("%s/%s", info.getCloudStorageLocation(), info.getFileName());
        var objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(info.getContentType());
        objectMetadata.setContentLength(info.getFileSize());
        amazonS3.putObject(properties.s3().getBucketName(), key, inputStream, objectMetadata);
    }

    @Override
    public InputStream download(String cloudStorageLocation, String fileName) {
        var key = String.format("%s/%s", cloudStorageLocation, fileName);
        var s3Object = amazonS3.getObject(properties.s3().getBucketName(), key);
        return s3Object.getObjectContent();
    }

    @Override
    public String getDownloadUrl(String cloudStorageLocation, String fileName, int expiredInMillis) {
        var key = String.format("%s/%s", cloudStorageLocation, fileName);
        var now = new Date();
        Date expiration = DateUtils.addMilliseconds(now, expiredInMillis);
        var presignedUrl = amazonS3.generatePresignedUrl(properties.s3().getBucketName(), key, expiration, HttpMethod.GET);
        return presignedUrl.toString();
    }
}
