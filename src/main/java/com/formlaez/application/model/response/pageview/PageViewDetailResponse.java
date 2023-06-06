package com.formlaez.application.model.response.pageview;

import com.formlaez.infrastructure.enumeration.PageViewDetailRedirectType;
import com.formlaez.infrastructure.enumeration.PageViewDetailType;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageViewDetailResponse {

    private PageViewDetailType type;

    private String customContent;
    private String redirectUrl;

    private PageViewDetailRedirectType redirectType;
}
