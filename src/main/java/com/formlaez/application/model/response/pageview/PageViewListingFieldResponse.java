package com.formlaez.application.model.response.pageview;

import com.formlaez.infrastructure.enumeration.PageViewListingFieldType;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageViewListingFieldResponse {
    private PageViewListingFieldType type;

    private String fieldCode;
    private String targetFieldCode;
    private String fixedValue;
}
