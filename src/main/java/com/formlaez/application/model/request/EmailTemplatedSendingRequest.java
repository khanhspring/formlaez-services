package com.formlaez.application.model.request;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailTemplatedSendingRequest {
    private String fromName;
    private String fromAddress;
    private List<String> toAddresses;
    private String templateId;
    private Map<String, String> data;
}
