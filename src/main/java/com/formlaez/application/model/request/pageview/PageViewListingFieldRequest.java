package com.formlaez.application.model.request.pageview;

import com.formlaez.infrastructure.enumeration.PageViewListingFieldType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageViewListingFieldRequest {
    @NotNull
    private PageViewListingFieldType type;

    @NotBlank
    private String fieldCode;
    private String targetFieldCode;
    private String fixedValue;
}
