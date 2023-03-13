package com.formlaez.application.model.request;

import com.formlaez.infrastructure.enumeration.AttachmentCategory;
import com.formlaez.infrastructure.enumeration.FormScope;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
