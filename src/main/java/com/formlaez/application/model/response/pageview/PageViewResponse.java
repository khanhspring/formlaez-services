package com.formlaez.application.model.response.pageview;

import com.formlaez.infrastructure.enumeration.PageViewStatus;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageViewResponse {
    private Long id;
    private String code;
    private String title;
    private String description;
    private PageViewStatus status;
    private PageViewTemplateResponse template;
    private List<PageViewListingFieldResponse> listingFields;

    private PageViewDetailResponse detail;
}
