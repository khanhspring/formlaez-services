package com.formlaez.infrastructure.model.common.attachment;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentMetadata {

    private String contentType;
    private String fileName;
    private long fileSize;
    private String cloudStorageLocation;
}
