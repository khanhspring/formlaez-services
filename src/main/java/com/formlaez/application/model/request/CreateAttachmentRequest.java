package com.formlaez.application.model.request;

import com.formlaez.infrastructure.enumeration.AttachmentCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAttachmentRequest {
    private String name;
    private String originalName;
    private String description;
    private String extension;
    private long size;
    private String type;
    private String cloudStorageLocation;
    private AttachmentCategory category;
}
