package com.formlaez.application.model.request.pageview;

import com.formlaez.infrastructure.enumeration.PageViewDetailRedirectType;
import com.formlaez.infrastructure.enumeration.PageViewDetailType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageViewDetailRequest {

    @NotNull
    private PageViewDetailType type;

    private String customContent;
    private String redirectUrl;

    private PageViewDetailRedirectType redirectType;
}
